<template>
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container>  
        <el-header>
            <el-container>
                娱乐内容评测论坛
                <el-button type="primary" @click="GotoUser">用户</el-button>
            </el-container>
        </el-header>
        <el-main>
            <el-tabs v-model="tab" type="card">
                <el-tab-pane label="作品查询" name="first">
                    <!-- 作品综合查询 -->
                    <el-container>
                        <el-input v-model="product.search" placeholder="请输入内容"></el-input>
                        <el-button type="primary" @click="SelectProduct">查询</el-button>
                        <el-button type="primary" @click="GotoProductSelect">深度搜索</el-button>
                    </el-container>
                    <el-table :data="product.data" style="width: 100%">
                        <el-table-column type="expand">
                        <!-- 作品自定义显示方式 -->
                        <template slot-scope="productData">
                            {{productData}}               
                            //显示作品名
                            //显示作品图
                            //显示作者
                        </template>
                        </el-table-column>
                    </el-table>
                    <!-- 分页设置 -->
                    <el-pagination @current-change="SelectProduct" :current-page.sync="product.currentPage"
                           :page-size="10" :pager-count="5" layout="prev, pager, next" :total="product.total"></el-pagination>
                </el-tab-pane>
                <el-tab-pane label="精选测评" name="second">
                    <!-- 精选测评显示 -->
                    <el-table :data="comment.data" style="width: 100%">
                        <el-table-column type="expand">
                        <!-- 评论自定义显示方式 -->
                        <template slot-scope="commentData">
                            {{commentData}}               
                        </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="排行大榜单" name="third">
                    <!-- 作品分类 -->
                    <el-dropdown @command="listType">
                    <span class="el-dropdown-link">
                        {{list.point}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command=0>{{list.type[0].data}}</el-dropdown-item>
                        <el-dropdown-item command=1>{{list.type[1].data}}</el-dropdown-item>
                        <el-dropdown-item command=2>{{list.type[2].data}}</el-dropdown-item>
                        <el-dropdown-item command=3>{{list.type[3].data}}</el-dropdown-item>
                        <el-dropdown-item command=4>{{list.type[4].data}}</el-dropdown-item>
                        <el-dropdown-item command=5>{{list.type[5].data}}</el-dropdown-item>
                    </el-dropdown-menu>
                    </el-dropdown>
                    <!-- 排行榜显示 -->
                    <el-table :data="list.data" style="width: 100%">
                        <el-table-column type="expand">
                        <!-- 作品自定义显示方式 -->
                        <template slot-scope="listData">
                            {{listData}}               
                        </template>
                        </el-table-column>
                    </el-table>
                    <!-- 分页设置 -->
                    <el-pagination @current-change="SelectList" :current-page.sync="list.currentPage"
                           :page-size="10" :pager-count="5" layout="prev, pager, next" :total="list.total"></el-pagination>
                </el-tab-pane>
            </el-tabs>
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
            tab: 'first', //默认标签
            product:{ //作品相关
                search:'', //搜索内容
                data:null, //数据
                currentPage:0, //当前页数
                length:0, //总页数
            },
            comment:{ //测评相关
                data:null, //数据
            },
            list:{ //排行榜相关
                point:'分类选择',  //分类选择指针
                type:[ //排行榜分类
                    { data:'全部' },
                    { data:'游戏' },
                    { data:'音乐' },
                    { data:'电影' },
                    { data:'小说' },
                    { data:'短视频' },
                ],
                data:null, //数据
                currentPage:0, //当前页数
                length:0, //总页数
            }, 
            user:{ //用户相关

            },
        }
    },
    mounted(){ //初始化自动执行工作
        //推荐生成

        //排行榜初始生成

    },
    methods: {
        //页面组件功能
        listType(command) { //排行榜下拉菜单的选择
            this.list.point = this.list.type[command].data
        },
        //具体数据交互
        SelectProduct(){//作品普通模糊查询
        
        },
        //测评查询
        
        
        SelectList(){//排行榜按分类搜索
            
        },
        //跳转：        
        GotoUser(){//用户管理跳转

        },
        GotoProductSelect(){//深度搜索跳转
        
        },
        GotoProduct(){//作品评测页跳转
        
        },
    }
}
</script>