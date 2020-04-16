#MyOIDC

<img src="http://andaily.com/blog/wp-content/uploads/2016/12/openid.png" alt="openid"/>
<p>
    基于OIDC协议的参考实现,根据各类库提供实现参考.
</p>
<p>
OIDC(OpenID Connect), 下一代的身份认证授权协议; 当前发布版本1.0;
<br/>
OIDC是基于OAuth2+OpenID整合的新的认证授权协议; OAuth2是一个授权(authorization)的开放协议, 在全世界得到广泛使用, 但在实际使用中,OAuth2只解决了授权问题, 没有实现认证部分,往往需要添加额外的API来实现认证; 而OpenID呢,是一个认证(authentication )的协议, 二者在实际使用过程中都有其局限性;
<br/>
综合二者,即是OIDC; 通过OIDC,既能有OAUTH2的功能,也有OpenID的功能; 恰到好处…
    <a href="http://andaily.com/blog/?p=440">查看完整介绍</a>
</p>
<hr/>

<h3>OIDC 协议</h3>
Version: 1.0
<br/>
<p>
    英文原版: <a href="http://openid.net/specs/openid-connect-core-1_0.html">http://openid.net/specs/openid-connect-core-1_0.html</a>
</p>


<p>
    JSON Web Signature(JWS): <a href="https://tools.ietf.org/html/rfc7515">https://tools.ietf.org/html/rfc7515</a>
</p>
<p>
    JSON Web Encryption(JWE): <a href="http://tools.ietf.org/html/draft-ietf-jose-json-web-encryption">http://tools.ietf.org/html/draft-ietf-jose-json-web-encryption</a>
</p>
<p>
    JSON Web Key(JWK): <a href="https://tools.ietf.org/html/draft-ietf-jose-json-web-key-41">https://tools.ietf.org/html/draft-ietf-jose-json-web-key-41</a>
</p>
<p>OIDC相关文章与介绍 <a href="http://andaily.com/blog/?s=OIDC">http://andaily.com/blog/?s=OIDC</a> </p>
<hr/>


<h3>项目介绍</h3>
<p>
    Maven项目, 字符编码: UTF-8; 基于TDD模式与DDD模式设计; 使用SpringBoot实现
</p>
<strong>使用的框架与版本号</strong>
<ul>
    <li><p>JDK (1.8.0_40)</p></li>
    <li><p>SprintBoot (2.1.4.RELEASE)</p></li>
    <li><p>Sprint-Security-OAuth (2.3.5.RELEASE)</p></li>
    <li><p>Jose4j (0.6.2)</p></li>
    <li><p>MySQL (5.7)</p></li>
</ul>

<strong>模块说明</strong>
<ul>
    <li><p>myoidc-server  - OpenID Provider[OP] 认证授权服务端</p></li>
    <li><p>myoidc-client  - Relying Party[RP] 客户端</p></li>
</ul>

<hr/>


<h3>功能列表</h3>
<p>介绍项目开发的计划与安排, 以及各个功能点</p>

<ul>
    <li><p>编写各个OIDC实现库的使用DEMO</p></li>
    <li><p>参考OIDC协议实现具体的流程</p></li>
    <li><p>项目整体基于<a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>扩展实现</p></li>
</ul>


<hr/>

<h3>项目日志</h3>

<ol>
    <li><p>2016-12-25  项目公开, 完善文档. 添加OIDC 库测试代码 <code>Jose4JTest.java</code>, <code>NimbusJoseJwtTest.java</code></p></li>
    <li><p>2016-07-12  开始尝试翻译OIDC协议为中文</p></li>
    <li><p>2017-01-21  加入GitHub https://github.com/monkeyk/MyOIDC, Git@OSC地址 http://gitee.com/mkk/MyOIDC</p>(与GitHub同步)</li>
    <li><p>2018-02-04  开始1.1.0分支, 使用SprintBoot重构, 开始受折磨的技术</p></li>
    <li><p>2020-03-10  1.1.0分支继续开发, 包括Endpoint API, 界面流程完善，EU,RP功能开发等; 详细查看文件<em>others/development-log.txt</em></p></li>
    <li><p>2020-04-16  1.1.0分支开发完成, v1.1.1开始分支</p></li>
</ol>

<hr/>

<h3>相关资源</h3>

<ul>
    <li><p>Open ID Connect网站 <a href="http://openid.net/connect/">http://openid.net/connect/</a> </p></li>
    <li><p>JSON Web Tokens(OIDC各类编程语言的类库) <a href="https://jwt.io/">https://jwt.io/</a> </p></li>
    <li><p>connect2id(OpenID Connect server for the enterprise) <a href="http://connect2id.com/">http://connect2id.com/</a> </p></li>
    <li><p>[认证授权] 4.OIDC（OpenId Connect）身份认证授权（核心部分） <a href="http://www.cnblogs.com/linianhui/p/openid-connect-core.html">http://www.cnblogs.com/linianhui/p/openid-connect-core.html</a> </p></li>
</ul>

<hr/>

<h3>国内OIDC 实践</h3>

<ul>
    <li><p>北京九州云腾科技有限公司 <a href="https://www.idsmanager.com/">https://www.idsmanager.com/</a></p></li>
    <li><p>阿里云API网关 OpenID Connect集成 <a href="https://help.aliyun.com/document_detail/48019.html">https://help.aliyun.com/document_detail/48019.html</a></p></li>
</ul>



<br/>
<strong>拥抱OIDC…</strong>

