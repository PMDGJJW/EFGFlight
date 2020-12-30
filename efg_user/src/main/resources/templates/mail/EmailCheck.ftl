conent = <!DOCTYPE html>
<html lang="en">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>

<h4>尊敬的${name}用户：</h4>
<h4>　　您好！</h4>
<p> 　　这是一条验证用户邮箱的注册信息，请在2小时之内进入以下链接，完成绑定邮箱功能
    （请千万不要将此链接泄露给他人）:</p>
　　<a href="${domain ! ''}/doUser/active/${token ! ''}">${domain ! ''}/doUser/active/${token ! ''}</a>
<p>　　如果浏览器不允许直接跳转，请将地址复制到浏览器的地址栏中访问
    如果您没有***系统管理员，那么这条邮件属于操作人员的失误，请您忽略此消息即可！
    (此邮件为系统自动发送，请勿回复)</p>
</body>
</html>
