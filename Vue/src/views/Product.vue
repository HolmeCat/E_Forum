<template>
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container>  
        <el-header>
            <el-page-header @back="Back" content="论坛评测区"></el-page-header>
        </el-header>
        <el-main>
            <!-- 显示作品名 -->

            <!-- 显示作品图 -->
            <!-- 显示作者 -->

            <!-- 显示类型，风格 -->

            <!-- 显示作品简介 -->

            <!-- 显示大众评分 -->

            <!-- 评论 + 去写评论按钮 -->

            <!-- 接下来显示评测帖子 -->
            <el-table :data="comment.data" style="width: 100%">
                <el-table-column type="expand">
                <!-- 评论自定义显示方式 -->
                <template slot-scope="commentData">
                    {{commentData}}      
                    <!-- 回复帖子自定义显示方式  -->

                </template>
                </el-table-column>
            </el-table>
        </el-main>
    </el-container>
    <!-- 右侧空白 -->
    <el-aside></el-aside>
</el-container>
</template>
<script>
export default {
    data() {
        return {
            product:null, //作品相关
            comment:{ //测评相关
                data:null, //数据
                currentPage:0, //当前页数
                length:0, //总页数
            },
            user:{ //用户相关

            },
        }
    },
    mounted(){ //初始化自动执行工作
        //作品信息读入

        //评测信息查询 & 回复贴信息查询

    },
    methods: {
        //页面组件功能

        //具体数据交互
        
        //评测帖信息查询

        //回复帖信息查询

        //评测帖增加

        //回复贴增加
        
        //跳转：
        Back(){ //返回主页

        }
    }
}
</script>