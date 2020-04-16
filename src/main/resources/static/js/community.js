/*一层回复*/
function post() {
    var questionId = $("#question_id").val();//根据id拿到questionid
    var content = $("#comment_content").val();//根据id拿到content文本
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {//封装了此方法，负责控制2种回复
    if (!content)//js里直接用!就能表示没有值，包括空null    服务器端的和前端都要判断，服务器的主要目的是不让错误信息存到数据库，前端是为了更快告诉用户
    {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({//利用ajax来实现异步刷新
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({    //json.stringify()把javascript对象转字符串
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();  //成功就刷新
            } else {
                if (response.code == 2003) {//没登陆
                    var isAccepted = confirm(response.message);//win自带框，显示错误信息,看用户是否要登陆
                    if (isAccepted) {//登陆
                        window.open("https://github.com/login/oauth/authorize?client_id=75c571318c6db9127268&redirect_uri=http://localhost:8887/callback&scope=user&state=1");//win自带方法，打开地址
                        window.localStorage.setItem("closable", true);//保存数据 key,value
                    }
                } else {//其他情况仅仅打印信息
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");//拿到dataid值
    var content = $("#input-" + commentId).val();//根据id拿到content文本
    comment2target(commentId, 2, content)
}

/*展开二级评论*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);//用来识别是哪一个
    $(comments).toggleClass("in");//每次点击都去增加/删除in来实现展示和显现
    $(e).toggleClass("active");//选中时显示蓝色
    if ($(comments).hasClass("in")) {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length == 1)//最开始有一个评论的标签，等于1就说明没加载过
        {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {//拿到全部子元素,reverse()是拿倒叙

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')//用了moment.js
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);//在父级前追加一个子元素
                });
            });
        }
    }
}