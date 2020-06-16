<template>
<div class="home1">
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container class="background">   
        <el-header>
            <el-page-header @back="Back" content="论坛测评区"></el-page-header>
        </el-header>
        <el-main>
            <!-- 显示作品名 -->
            <div class="name">{{product.name}}</div>
             <!-- 显示大众评分 -->
            <el-rate v-model="product.score" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
            <!-- 显示作者 -->
            <br>作者：{{product.author}}
            <!-- 显示类型，风格 -->
            <br>{{product.type}}/{{product.style}}
            <!-- 显示作品简介 -->
            <br><br>作品简介：<br>{{product.introduction}}
            <!-- 接下来显示评测帖子 -->
            <br><br>评论：
            <el-table :data="comment.data" style="width: 100%">
                <el-table-column>
                <!-- 评论自定义显示方式 -->
                <template slot-scope="commentData">
                    {{commentData.row.user.name}}{{commentData.row.date}}
                    <el-rate v-model="commentData.row.score" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
                    <br>{{commentData.row.context}}
                    <!-- 回复帖子自定义显示方式  -->
                    <el-collapse @change="SelectReply(commentData.row)">
                        <el-collapse-item title="查看回复" name="1">
                            <el-table :data="commentData.row.reply" style="width: 100%">
                                <el-table-column>
                                    <template slot-scope="replyData">
                                        {{replyData.row.user.name}}{{replyData.row.date}}
                                        {{replyData.row.context}}       
                                    </template>
                                </el-table-column>
                            </el-table>
                            <el-divider></el-divider>
                            <!-- 添加回复区 -->
                            <el-form :model="reply" ref="reply" class="demo-ruleForm">
                                <el-form-item prop="update" label="添加回复" 
                                    :rules="[{ required: true, message: '回复内容不可为空！'}]">
                                    <el-input type="textarea" placeholder="请输入内容" v-model="reply.update" maxlength="200" show-word-limit></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="InsertReply(commentData.row)">添加回复</el-button>
                                </el-form-item>
                            </el-form>
                        </el-collapse-item>
                    </el-collapse>
                </template>
                </el-table-column>
            </el-table>
            <!-- 分页设置 -->
            <el-pagination @current-change="SelectComment" :current-page.sync="comment.currentPage"
                    :page-size="10" :pager-count="5" layout="prev, pager, next" :total="comment.total"></el-pagination>
            <el-divider></el-divider>
            <!-- 打分和写评论 -->
            <el-form :model="comment" ref="comment" class="demo-ruleForm">
                <el-form-item label="上星打分">
                    <br><el-rate v-model="comment.rate" allow-half show-score text-color="#ff9900" score-template="{value}"></el-rate>
                </el-form-item>
                <el-form-item prop="update" label="添加评论" 
                    :rules="[{ required: true, message: '评论内容不可为空！'}]">
                    <el-input type="textarea" placeholder="请输入内容" v-model="comment.update" maxlength="200" show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="InsertComment">添加评论</el-button>
                </el-form-item>
            </el-form>            
        </el-main>
    </el-container>
    <!-- 右侧空白 -->
    <el-aside></el-aside>
</el-container>
</div>
</template>
<script>
export default {
    data() {
        return {
            product:{}, //作品相关
            comment:{ //测评相关
                data:[ //数据
                    {reply:null} //回复帖数据
                ], 
                currentPage:0, //当前页数
                length:0, //总页数
                update:null, //添加评论
                rate:0, //打分
            },
            reply:{
                update:null,
            },
            user:{ //用户相关
                name:null,
            },
        }
    },
    mounted(){ //初始化自动执行工作
        //登录Session录入，如果有就认为是登陆在线，否则就默认null
        if(this.$session.has('userName')){
            //写入变量name
            this.user.name = this.$session.get('userName')
        }
        //作品信息读入
        this.product = this.$route.query,
        //评测信息查询
        this.SelectComment()
    },
    methods: {
        //页面组件功能
        //具体数据交互
        SelectComment(){//评测帖信息查询
            this.axios({//根据关键字做查询
                method:'get',
                url:'http://localhost:8085/comment/selectAllByProductId',
                params: {
                    id:this.product.id, //参数为类型
                    page:this.comment.currentPage,
                    size:10
                }
            }).then(response => { //返回列表清单
                if (response.status == 200){
                    this.comment.data = response.data.content,
                    this.comment.total = response.data.totalPages
                }
            });
        },
        SelectReply(data){//回复帖信息查询
            this.axios({//根据关键字做查询
                method:'get',
                url:'http://localhost:8085/reply/selectAllByCommentId',
                params: {
                    id:data.id
                }
            }).then(response => { //返回列表清单
                if (response.status == 200){
                    data.reply = response.data
                }
            });
        },
        InsertComment(){//评测帖增加
            //如果没登录，提示请先登录
            if(this.user.name == null){
                this.$alert('尚未登录！');
            }
            else{//已经登录的话，执行添加
                this.$refs['comment'].validate((valid) => {
                    if (valid) {
                        this.axios({//根据内容添加
                            method:'get',
                            url:'http://localhost:8085/comment/insertOne',
                            params: {
                                context:this.comment.update,
                                score:this.comment.rate,
                                productId:this.product.id,
                                userName:this.user.name
                            }
                        }).then(response => { //返回列表清单
                            if (response.status == 200){
                                //添加成功
                                this.$alert('添加成功！');
                                this.$router.go(0) //刷新页面
                            }
                        });
                    } else {
                        return false;
                    }
                });
            }
        },
        InsertReply(data){//回复贴增加
            //如果没登录，提示请先登录
            if(this.user.name == null){
                this.$alert('尚未登录！');
            }
            else{//已经登录的话，执行添加
                this.$refs['reply'].validate((valid) => {
                    if (valid) {
                        this.axios({//根据内容添加
                            method:'get',
                            url:'http://localhost:8085/reply/insertOne',
                            params: {
                                context:this.reply.update,
                                commentId:data.id,
                                userName:this.user.name
                            }
                        }).then(response => { //返回列表清单
                            if (response.status == 200){
                                //添加成功
                                this.$alert('添加成功！');
                                this.$router.go(0) //刷新页面
                            }
                        });    
                    } else {
                        return false;
                    }
                });
            }
        },
        //跳转：
        Back(){ //返回主页
            this.$router.go(-1)  //刷新页面
        }
    }
}
</script>
<style>
    .home1{
        background: url('../img/6.jpg');
        position:relative;
        margin-left: -8px;
        margin-top: -6px;
        padding: 7px;
        background-size: cover;
        width: 100%;
        height: 600px;
    }
    .background{
        background-color: white;
    }
    .name{
        font-size: 40px;
        color: gray;
    }
</style>