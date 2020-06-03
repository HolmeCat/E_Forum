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
                <el-tab-pane label="作品综合查询" name="first">
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
                        </template>
                        </el-table-column>
                    </el-table>
                    <!-- 分页设置 -->
                    <el-pagination @current-change="SelectProduct" :current-page.sync="product.currentPage"
                           :page-size="10" :pager-count="5" layout="prev, pager, next" :total="product.total"></el-pagination>
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
            user:{ //用户相关

            },
        }
    },
    mounted(){ //初始化自动执行工作


    },
    methods: {

        //具体数据交互


        //跳转：        
        Back(){ //返回主页
        
        },
    }
}
</script>