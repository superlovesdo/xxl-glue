package com.xxl.glue.core.broadcast;

import com.xxl.glue.core.GlueFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * zookeeper service registry
 * @author xuxueli 2015-10-29 14:43:46
 */
public class ZkTopicConsumerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZkTopicConsumerUtil.class);

	// ------------------------------ zookeeper client ------------------------------
	private static ZooKeeper zooKeeper;
	private static ReentrantLock INSTANCE_INIT_LOCK = new ReentrantLock(true);
	private static ZooKeeper getInstance(){
		if (zooKeeper==null) {
			try {
				if (INSTANCE_INIT_LOCK.tryLock(2, TimeUnit.SECONDS)) {

                    try {
                        // init zookeeper
                        /*final CountDownLatch countDownLatch = new CountDownLatch(1);
                        countDownLatch.countDown();
                        countDownLatch.await();*/
                        zooKeeper = new ZooKeeper(Environment.ZK_ADDRESS, 10000, new Watcher() {
                            @Override
                            public void process(WatchedEvent event) {

                                try {
                                    // session expire, close old and create new
                                    if (event.getState() == Event.KeeperState.Expired) {
                                        zooKeeper.close();
                                        zooKeeper = null;
                                        getInstance();
                                    }
                                } catch (InterruptedException e) {
                                    logger.error("", e);
                                }

                                // refresh service address
                                if (event.getType() == Event.EventType.NodeDataChanged){
                                    String path = event.getPath();
                                    if (path!=null && path.startsWith(Environment.GLUE_TOPIC_PATH)) {
                                        // add one-time watch
                                        try {
                                            zooKeeper.exists(path, true);
                                        } catch (Exception e) {
                                            logger.error("", e);
                                        }
                                        // broadcase message
                                        String name = path.substring(Environment.GLUE_TOPIC_PATH.length()+1, path.length());
                                        String data = null;
                                        try {
                                            byte[] resultData = zooKeeper.getData(path, true, null);
                                            if (resultData != null) {
                                                data = new String(resultData);
                                            }

                                            // clean cache
                                            GlueMessage glueMessage = JacksonUtil.readValue(data, GlueMessage.class);
                                            GlueFactory.clearCache(glueMessage);
                                        } catch (Exception e) {
                                            logger.error("", e);
                                        }

                                    }
                                }

                            }
                        });

                        // init glue tipic path
                        Stat stat =zooKeeper.exists(Environment.GLUE_TOPIC_PATH, false);
                        if (stat == null) {
                            zooKeeper.create(Environment.GLUE_TOPIC_PATH, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }

                        logger.info(">>>>>>>>> xxl-rpc zookeeper connnect success.");
                    } finally {
                        INSTANCE_INIT_LOCK.unlock();
                    }
				}
			} catch (InterruptedException e) {
				logger.error("", e);
			} catch (IOException e) {
				logger.error("", e);
			} catch (KeeperException e) {
				logger.error("", e);
			} finally {
				INSTANCE_INIT_LOCK.unlock();
			}
		}
		if (zooKeeper == null) {
			throw new NullPointerException(">>>>>>>>>>> xxl-rpc, zookeeper connect fail.");
		}
		return zooKeeper;
	}

	// ------------------------------ topic watch ------------------------------
	/**
	 * register service
	 */
	public static void watchTopic(String topicKey) throws KeeperException, InterruptedException {

		// "topic key" path : /xxl-rpc/topic-key
		String topicKeyPath = Environment.GLUE_TOPIC_PATH.concat("/").concat(topicKey);

		// watch
		Stat topicKeyPathStat = getInstance().exists(topicKeyPath, true);
		if (topicKeyPathStat == null) {
			getInstance().create(topicKeyPath, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			getInstance().exists(topicKeyPath, true);
		}

		logger.info(">>>>>>>>>>> xxl-rpc topic consumer watch topic item, topicKey:{}, topicKeyPath:{}", topicKey, topicKeyPath);
	}

	// ------------------------------ topic broadcast ------------------------------
	public static Stat broadcast(GlueMessage message) {

		String name = message.getName();
		String data = JacksonUtil.writeValueAsString(message);

		try {

			// "base" path : /xxl-rpc
			Stat stat = getInstance().exists(Environment.GLUE_TOPIC_PATH, false);
			if (stat == null) {
				getInstance().create(Environment.GLUE_TOPIC_PATH, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

			// "topic key" path : /xxl-rpc/topic-key
			String topicKeyPath = Environment.GLUE_TOPIC_PATH.concat("/").concat(name);

			// watch
			Stat topicKeyPathStat = getInstance().exists(topicKeyPath, false);
			if (topicKeyPathStat == null) {
				getInstance().create(topicKeyPath, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				topicKeyPathStat = getInstance().exists(topicKeyPath, false);
			}

			Stat ret = zooKeeper.setData(topicKeyPath, data.getBytes(), topicKeyPathStat.getVersion());
			return ret;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}