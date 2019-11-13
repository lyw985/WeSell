################################################
΢Źڰ̨--½ҵ
2014-04-15
################################################
ҵΪ¼
1.½ҵؼ
:,ؼΪxwlb

2.΢Źʺ
ģʽ
URL:http://cnet.hodanet.com/WeChatServlet?serverCode=xwlb
Token:hangzhouhodanet_xwlb

3.ͨcommon.properties
serverNames=blsh,xwlb
xwlb.token=hangzhouhodanet_xwlb
xwlb.appid=<΢ʺſģʽAppId>
xwlb.appsecret=<΢ʺſģʽAppSecret>

4.صظΪ XwlbWeixinServiceImpl
һ㽨鸴blshش,ؼblshΪxwlb

5.ݿش,applicationContext.xml
<property name="packagesToScan">
	<list>
		<value>com.hodanet.system.entity.po</value>
		<value>com.hodanet.common.entity.po</value>
		<value>com.hodanet.weixin.entity.po</value>
		<value>com.hodanet.blsh.entity.po</value>
		<!-- Ҫ -->
		<value>com.hodanet.xwlb.entity.po</value>
	</list>
</property>

6.WEB-INF/view/ҳ

7.̨վ-Դ

Ϣ:	/weixin/msg/list.do?serverCode=xwlb
˵:	/weixin/menu/list.do?serverCode=xwlb
ע߹:	/weixin/follower/list.do?serverCode=xwlb

ע:
1.AppIdAppSecretҪ΢֤,֤֮ǰxwlb.appidxwlb.appsecret,URLԷʵķ
2.ӿʹҪȨ,ĺźͷ,綩ĺŲԶ˵,ſԶ˵, {"errcode":48001,"errmsg":"api unauthorized"} ǽӿδɵ
3.ӿʹƵ,http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%8F%A3%E9%A2%91%E7%8E%87%E9%99%90%E5%88%B6%E8%AF%B4%E6%98%8E