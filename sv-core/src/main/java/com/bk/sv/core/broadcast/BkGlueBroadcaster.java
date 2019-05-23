package com.bk.sv.core.broadcast;

import com.bk.sv.core.SvFactory;
import com.bk.sv.core.broadcast.zk.BkZkBroadcastWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * sv broadcast
 *
 * @author BK 2015-10-29 14:43:46
 */
public class BkGlueBroadcaster extends BkZkBroadcastWatcher {
    private static final Logger logger = LoggerFactory.getLogger(BkGlueBroadcaster.class);
    /**
     * broadcast topic : /sv/glueKey
     */
    private static final String EXECUTION_UNIT_BASE = "/sv";
    private static final String EXECUTION_UNIT_BROADCAST = EXECUTION_UNIT_BASE + "/broadcast";
    private static BkGlueBroadcaster instance = null;

    public BkGlueBroadcaster(String zkserver) {
        super(zkserver);
        instance = this;
    }

    public static BkGlueBroadcaster getInstance() {
        return instance;
    }

    /**
     * procuce msg
     *
     * @param glueName
     * @param appnames
     * @param version
     * @return
     */
    public boolean productMsg(String glueName, Set<String> appnames, long version) {
        String topicPath = EXECUTION_UNIT_BROADCAST + "/" + glueName;

        SvMessage message = new SvMessage();
        message.setGlueName(glueName);
        message.setAppnames(appnames);
        message.setVersion(version);
        String data = JacksonUtil.writeValueAsString(message);

        return super.produce(topicPath, data);
    }

    /**
     * watch msg
     *
     * @param name
     * @return
     */
    public boolean watchMsg(String name) {
        String topic = EXECUTION_UNIT_BROADCAST + "/" + name;

        return watchTopic(topic);
    }

    /**
     * consume msg
     *
     * @param path
     * @param data
     */
    @Override
    public void consumeMsg(String path, String data) {
        SvMessage svMessage = JacksonUtil.readValue(data, SvMessage.class);
        SvFactory.refresh(svMessage);
    }

}