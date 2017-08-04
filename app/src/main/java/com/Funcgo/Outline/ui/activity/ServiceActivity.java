package com.Funcgo.Outline.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.Funcgo.Outline.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/6/22.
 */

public class ServiceActivity extends BaseActivity {
    @BindView(R.id.iv_menus)
    ImageView ivMenus;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceshow);
        ButterKnife.bind(this);

        WebSettings settings = webview.getSettings();
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.SMALLEST);
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("javascript:方法名");
//        WebAPI.getProtocalData(new AggAsyncHttpResponseHandler(this, new AggAsyncHttpResponseHandler.CallBack() {
//            @Override
//            public void onSuccess(String data) {
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    JSONObject data1 = jsonObject.getJSONObject("data");
//                    String protocol_content = data1.getString("protocol_content");
//                    protocol_content = protocol_content.replace("\n","");
//                    webview.loadData(protocol_content, "text/html; charset=UTF-8", null);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }));
        String protocol_content = "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>#### 关于服务</title>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<style type=\"text/css\">\n" +
                "html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0}\n" +
                "body{font-family:Helvetica,arial,freesans,clean,sans-serif;font-size:8px;line-height:1;color:#333;background-color:#fff;padding:20px;max-width:960px;margin:0 auto}\n" +
                "body>*:first-child{margin-top:0 !important}body>*:last-child{margin-bottom:0 !important}\n" +
                "p,blockquote,ul,ol,dl,table,pre{margin:0px 0}\n" +
                "h1,h2,h3,h4,h5,h6{margin:0px 0 5px;padding:0;font-weight:bold;-webkit-font-smoothing:antialiased}\n" +
                "h1 tt,h1 code,h2 tt,h2 code,h3 tt,h3 code,h4 tt,h4 code,h5 tt,h5 code,h6 tt,h6 code{font-size:inherit}h1{font-size:28px;color:#000}h2{font-size:24px;border-bottom:1px solid #ccc;color:#000}\n" +
                "h3{font-size:18px}\n" +
                "h4{font-size:16px}\n" +
                "h5{font-size:14px}\n" +
                "h6{color:#777;font-size:14px}\n" +
                "body>h2:first-child,body>h1:first-child,body>h1:first-child+h2,body>h3:first-child,body>h4:first-child,body>h5:first-child,body>h6:first-child{margin-top:0;padding-top:0}\n" +
                "a:first-child h1,a:first-child h2,a:first-child h3,a:first-child h4,a:first-child h5,a:first-child h6{margin-top:0;padding-top:0}h1+p,h2+p,h3+p,h4+p,h5+p,h6+p{margin-top:0px}\n" +
                "a{color:#4183c4;text-decoration:none}\n" +
                "a:hover{text-decoration:underline}\n" +
                "ul,ol{padding-left:30px}\n" +
                "ul li>:first-child,ol li>:first-child,ul li ul:first-of-type,ol li ol:first-of-type,ul li ol:first-of-type,ol li ul:first-of-type{margin-top:0}\n" +
                "ul ul,ul ol,ol ol,ol ul{margin-bottom:0}\n" +
                "dl{padding:0}\n" +
                "dl dt{font-size:14px;font-weight:bold;font-style:italic;padding:0;margin:0px 0 0px}\n" +
                "dl dt:first-child{padding:0}\n" +
                "dl dt>:first-child{margin-top:0}\n" +
                "dl dt>:last-child{margin-bottom:0}\n" +
                "dl dd{margin:0px;padding:0 15px}\n" +
                "dl dd>:first-child{margin-top:0}\n" +
                "dl dd>:last-child{margin-bottom:0}\n" +
                "pre,code,tt{font-size:12px;font-family:Consolas,\"Liberation Mono\",Courier,monospace}\n" +
                "code,tt{margin:0;padding:0;white-space:nowrap;border:1px solid #eaeaea;background-color:#f8f8f8;border-radius:3px}\n" +
                "pre>code{margin:0;padding:0;white-space:pre;border:0;background:transparent}\n" +
                "pre{background-color:#f8f8f8;border:1px solid #ccc;font-size:13px;line-height:19px;overflow:auto;padding:6px 10px;border-radius:3px}\n" +
                "pre code,pre tt{background-color:transparent;border:0}\n" +
                "blockquote{border-left:4px solid #DDD;padding:0 15px;color:#777}\n" +
                "blockquote>:first-child{margin-top:0}\n" +
                "blockquote>:last-child{margin-bottom:0}\n" +
                "hr{clear:both;margin:0px 0;height:0;overflow:hidden;border:0;background:transparent;border-bottom:4px solid #ddd;padding:0}\n" +
                "table th{font-weight:bold}table th,table td{border:1px solid #ccc;padding:6px 13px}\n" +
                "table tr{border-top:1px solid #ccc;background-color:#fff}\n" +
                "table tr:nth-child(2n){background-color:#f8f8f8}\n" +
                "img{max-width:100%}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h4>关于服务</h4>\n" +
                "<h5>本服务内容</h5>\n" +
                "<h6>本服务内容是指（OutLine）通过合法的互联网技术，向个人用户提供跨平台的数据加密传输服务。</h6>\n" +
                "<h5>本服务的用途</h5>\n" +
                "<h6>1.   本服务仅允许个人非商业化使用（团队企业版除外）。</h6>\n" +
                "<h6>2.   本服务不可用于危害互联网安全，侵犯他人知识产权等违法行为</h6>\n" +
                "<h5>本服务的可用性</h5>\n" +
                "<h6>1.   如因服务器基础线路故障，检修等问题导致服务中断，我们将给予用户一定的服务时长补偿。</h6>\n" +
                "<h6>2.   由于用户网络环境和复杂性，我们无法确保服务的持续可用。因此，您因本服务的短暂或长时间中断而遭受的损失，我们会提供维护工程师进行远程指导或上门服务（上门服务仅限团队或企业版）。</h6>\n" +
                "<h5>本服务许可的范围</h5>\n" +
                "<h6>（OutLine）给予您个人的，非商业化的，不可转让及非排他性的使用许可，以使用本服务。您可以以非商业的目的在多台设备上配置本服务，但同时的设备不可超过5台，否则账户将被停止使用。</h6>\n" +
                "<h5>被禁止的行为</h5>\n" +
                "<h6>1.   禁止用于诈骗,色情,赌博,攻击,非法舆论等非法操作；</h6>\n" +
                "<h6>2.   禁止使用非下载线路下载侵犯他人版权的影音文件。</h6>\n" +
                "<h6>3.   禁止使用任何线路发送垃圾邮件，或论坛注水、滥发广告等行为。</h6>\n" +
                "<h6>4.   以及任何违反国家法律、法规,危害国家安全、社会稳定的事情也是被禁止的。</h6>\n" +
                "<h6>5.   以上行为一经发现，我们有权停止您的账户，并且不予退款。不法行为者，将配合有关部门进行调查。</h6>\n" +
                "<h5>服务的更新</h5>\n" +
                "<h6>为了改善用户体验，完善服务内容，（OutLine）将不断开发新的服务，并不时提供服务或软件更新（这些更新可能会以替换、修改、功能强化、版本升级等形式提交给您）。</h6>\n" +
                "<h6>随着本服务新版本发布，旧版本的服务可能会随之无法使用。我们不保证旧版产品的可用性，请您随时核对并使用最新版本。</h6>\n" +
                "<h5>第三方软件或技术</h5>\n" +
                "<h6>1.   您理解并同意：（OutLine）为了向您提供有效的服务，会使用您终端设备的处理器和带宽等资源。本服务所示套餐内流量为内部计费设置，不代表您使用过程产生的数据流量费用，用户需自行向运营商了解相关资费信息，并自行承担相关费用。</h6>\n" +
                "<h6>2.   使用的第三方软件或技术（包括本服务可能使用的开源代码和公共领域代码等，下同）已经获得合法授权。</h6>\n" +
                "<h6>3.   因为使用的第三方软件或技术而引发的任何纠纷，应由该对应的第三方负责解决，（OutLine）不承担任何责任，亦无法对第三方软件或技术提供客户支持，若您需要获取支持，请与对应的第三方联系。</h6>\n" +
                "<h5>隐私保护</h5>\n" +
                "<h6>1.   为保护用户个人隐私，我方不会透露用户的个人信息！除非有法律相关规定，或者行政、司法机构要求，我们会配合相关行政、司法机构调查。</h6>\n" +
                "<h6>2.   为保护用户个人隐私，我方不会透露用户的个人信息！除非有法律相关规定，或者行政、司法机构要求，我们会配合相关行政、司法机构调查。</h6>\n" +
                "<h5>退款政策</h5>\n" +
                "<h6>用户在充值后的三天之内，可无条件通过填写后台工单联系客服说明原因后，申请退款事宜。退款工单提交后或联系客服回复后，我们会在5-15个工作日内做退款处理。退款到账时间视充值平台的处理情况而定</h6>\n" +
                "<h5>其他</h5>\n" +
                "<h6>1.    您使用本服务即视为您已阅读并同意受本协议的约束。（OutLine）有权在必要时修改本协议条款。您可以在网站中查阅相关协议的最新条款。本协议条款变更后，如果您继续使用（OutLine），即视为您已接受修改后的协议。如果您不接受修改后的协议，应当停止使用本服务。</h6>\n" +
                "<h6>2.    本协议条款无论因何种原因部分无效或不可执行，其余条款仍有效，对双方具有约束力。</h6>\n" +
                "<h6>3.    最终解释权归（天津函数科技有限公司）所有。</h6>\n" +
                "<!-- This document is made by md2html, website is http://www.zai17.com/md2html -->\n" +
                "<!-- Use style in style1-->\n" +
                "</body>\n" +
                "</html>";
        webview.loadData(protocol_content, "text/html; charset=UTF-8", null);

//        final String linkCss = "<style type=\"text/css\"> " +
//                "body {text-align:justify;font-size: 12px; line-height: 18px}" +
//                "</style>";
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("DD", "=======" + url);
//                webview.loadData(linkCss, "text/html", "utf-8");
////                view.loadUrl(url);
//                return true;
//            }
//        });


    }

    @OnClick(R.id.iv_menus)
    public void onViewClicked() {
        finish();
    }
}
