#MyOIDC

<img src="https://andaily.com/blog/wp-content/uploads/2016/12/openid.png"/>
<p>
    基于OIDC协议的参考实现,根据各类库提供实现参考.
</p>
<p>
OIDC(OpenID Connect), 下一代的身份认证授权协议; 当前发布版本1.0;
<br/>
OIDC是基于OAuth2+OpenID整合的新的认证授权协议; OAuth2是一个授权(authorization)的开放协议, 在全世界得到广泛使用, 但在实际使用中,OAuth2只解决了授权问题, 没有实现认证部分,往往需要添加额外的API来实现认证; 而OpenID呢,是一个认证(authentication )的协议, 二者在实际使用过程中都有其局限性;
<br/>
综合二者,即是OIDC; 通过OIDC,既能有OAUTH2的功能,也有OpenID的功能; 恰到好处…
    <a href="https://andaily.com/blog/?p=440">查看完整介绍</a>
</p>
<hr/>

<h3>OIDC 协议</h3>
Version: 1.0
<p>
    中文版:  <a href="https://monkeyk.com/oidc/Final_OpenID-Connect-Core-1.0-incorporating-errata-set-1_CN.html">https://monkeyk.com/oidc/Final_OpenID-Connect-Core-1.0-incorporating-errata-set-1_CN.html</a>    (翻译中)
    <br/>
    翻译最新进展请访问:<br/> <a href="http://git.oschina.net/shengzhao/spring-oauth-server/tree/master/others/oidc?dir=1&filepath=others%2Foidc&oid=ec2b907c58efcb56a8c774e8f7868ab80c6cb69a&sha=25a0e1f2357768eadf3f0e3b8035959e3bbd3d66">http://git.oschina.net/shengzhao/spring-oauth-server/tree/master/others/oidc?dir=1&filepath=others%2Foidc&oid=ec2b907c58efcb56a8c774e8f7868ab80c6cb69a&sha=25a0e1f2357768eadf3f0e3b8035959e3bbd3d66</a>
</p>
<p>
    英文原版: <a href="http://openid.net/specs/openid-connect-core-1_0.html">http://openid.net/specs/openid-connect-core-1_0.html</a>
</p>
<p>
    JSON Web Signature(JWS): <a href="https://tools.ietf.org/html/rfc7515">https://tools.ietf.org/html/rfc7515</a>
</p>

<hr/>


<h3>项目介绍</h3>
<p>
    Maven项目, 字符编码: UTF-8; 基于TDD模式与DDD模式设计
</p>
<strong>使用的框架与版本号</strong>
<ul>
    <li><p>JDK (1.8.0_40)</p></li>
    <li><p>Servlet (3.1.0)</p></li>
    <li><p>Jose4j (0.5.3)</p></li>
    <li><p>Spring (4.1.6.RELEASE)</p></li>
    <li><p>Log4j (1.2.14)</p></li>
</ul>


<hr/>


<h3>功能列表</h3>
<p>介绍项目开发的计划与安排, 以及各个功能点</p>

<ul>
    <li><p>编写各个OIDC实现库的使用DEMO</p></li>
    <li><p>参考OIDC协议实现具体的流程</p></li>
</ul>


<hr/>

<h3>项目日志</h3>

<ol>
    <li><p>2016-12-25  项目公开, 完善文档</p></li>
    <li><p>2016-07-12  开始尝试翻译OIDC协议为中文</p></li>
</ol>

<hr/>

<h3>相关资源</h3>

<ul>
    <li><p>Open ID Connect网站 <a href="http://openid.net/connect/">http://openid.net/connect/</a> </p></li>
    <li><p>OIDC相关文章与介绍 <a href="https://andaily.com/blog/?s=OIDC">https://andaily.com/blog/?s=OIDC</a> </p></li>
    <li><p>JSON Web Tokens(OIDC各类编程语言的类库) <a href="https://jwt.io/">https://jwt.io/</a> </p></li>
</ul>



<br/>
<strong>拥抱OIDC…</strong>

