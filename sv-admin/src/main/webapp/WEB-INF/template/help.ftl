<#import "/common/common.macro.ftl" as netCommon>
<!DOCTYPE html>
<html>
<head>
    <title><@netCommon.commonName /></title>
    <@netCommon.commonStyle />
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && "off" == cookieMap["adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
        <!-- left -->
    <@netCommon.commonLeft "help" />

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>使用教程
                    <small></small>
                </h1>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="callout callout-info">
                    <h4>简介：sv</h4>
                    <br>
                    <p>
                        <a target="_blank" href="https://github.com/xuxueli/sv">github地址</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <iframe src="https://ghbtns.com/github-btn.html?user=xuxueli&repo=sv&type=star&count=true"
                                frameborder="0" scrolling="0" width="170px" height="20px"
                                style="margin-bottom:-5px;"></iframe>
                        <br><br>
                        <a target="_blank" href="https://my.oschina.net/xuxueli/blog/732499">oschina地址</a>
                        <br><br>
                        <a target="_blank" href="http://www.xuxueli.com/page/community.html">社区交流</a>
                        <br><br>

                    </p>
                    <p></p>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <!-- footer -->
    <@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
