<template>
<div class="home1">
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container class="background">  
        <el-header>
            <el-container>
                <el-page-header @back="Back" content="深度搜索区"></el-page-header>                
                <el-button type="primary" @click="GotoUser">欢迎您！{{user.name}}</el-button>
                <!-- 用户登录弹窗，只有未登录才会打开 -->
                <el-dialog title="用户登录与注册" :visible.sync="user.dialogFormVisible"  width="30%" center>
                    <el-form :model="user" ref="user" class="demo-ruleForm">
                        <el-form-item prop="updateName" label="用户名" 
                            :rules="[{ required: true, message: '用户名不可为空！'}]">
                            <el-input v-model="user.updateName" placeholder="请输入用户名" maxlength="20" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item prop="password" label="密码" 
                            :rules="[{ required: true, message: '密码不可为空！'}]">
                            <el-input type="password" v-model="user.password" placeholder="请输入密码" maxlength="20" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item center>                            
                            <el-button type="primary" @click="LogIn">登录</el-button>
                            <el-button @click="Register">注册</el-button>                         
                        </el-form-item>
                    </el-form>          
                </el-dialog>
            </el-container>
        </el-header>
        <el-main>
            <!-- 作品综合查询表单 -->            
            <el-form :inline="true" :model="product" ref="product" class="demo-ruleForm">
                <el-form-item prop="search.name" label="作品名称">
                    <el-input v-model="product.search.name" maxlength="50" show-word-limit></el-input>
                </el-form-item>
                <el-form-item prop="search.introduction" label="作品简介模糊查询">
                    <el-input v-model="product.search.introduction" maxlength="50" show-word-limit></el-input>
                </el-form-item>
                <el-form-item prop="search.author" label="作者名称">
                    <el-input v-model="product.search.author" maxlength="50" show-word-limit></el-input>
                </el-form-item>
                <el-form-item prop="search.style" label="风格字段模糊查询">
                    <el-input v-model="product.search.style" maxlength="50" show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="SelectProduct">查询</el-button>
                </el-form-item>
                <!-- 作品分类 -->
                <br>类型选择：
                <el-dropdown @command="productType">
                    <span class="el-dropdown-link">
                        {{this.product.search.point}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command=0>{{product.search.type[0].data}}</el-dropdown-item>
                        <el-dropdown-item command=1>{{product.search.type[1].data}}</el-dropdown-item>
                        <el-dropdown-item command=2>{{product.search.type[2].data}}</el-dropdown-item>
                        <el-dropdown-item command=3>{{product.search.type[3].data}}</el-dropdown-item>
                        <el-dropdown-item command=4>{{product.search.type[4].data}}</el-dropdown-item>
                        <el-dropdown-item command=5>{{product.search.type[5].data}}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form>
            <el-divider></el-divider>
            <el-table :data="product.data" style="width: 100%">
                <el-table-column>
                <!-- 作品自定义显示方式 -->
                <template slot-scope="productData">               
                    <el-button type="text" @click="GotoProduct(productData.row)">{{productData.row.name}}</el-button>
                    <el-rate v-model="productData.row.score" disabled show-score text-color="#ff9900"
                        score-template="{value}"></el-rate>
                    {{productData.row.author}}
                    {{productData.row.type}}/{{productData.row.style}}
                </template>
                </el-table-column>
            </el-table>
            <!-- 分页设置 -->
            <el-pagination @current-change="SelectProduct" :current-page.sync="product.currentPage"
                    :page-size="10" :pager-count="5" layout="prev, pager, next" :total="product.total"></el-pagination>
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
            tab: 'first', //默认标签
            product:{ //作品相关
                search:{ //搜索内容
                    name:'',
                    introduction:'',
                    author:'',
                    point:'全部',  //分类选择指针
                    type:[ //排行榜分类
                        { data:'全部'},
                        { data:'游戏' },
                        { data:'音乐' },
                        { data:'电影' },
                        { data:'图书' },
                        { data:'综艺视频' },
                    ],
                    style:''
                }, 
                data:null, //数据
                currentPage:0, //当前页数
                total:0, //总页数
            },
            user:{ //用户相关
                name:'未登录', //用户登录名称记录
                updateName:null, //登录和注册的用户名变量
                password:null, //用户登录密码
                dialogFormVisible: false//登陆弹窗控制
            },
        }
    },
    mounted(){ //初始化自动执行工作
        //登录Cookie录入，如果有就认为是登陆在线，否则就默认null
        if(this.$session.has('userName')){
            //写入变量name
            this.user.name = this.$session.get('userName')
        }
    },
    methods: {
        //页面组件功能
        productType(command) { //排行榜下拉菜单的选择
            this.product.search.point = this.product.search.type[command].data
        },
        //具体数据交互
        SelectProduct(){//作品综合模糊查询
            this.axios({//根据关键字做查询
                method:'get',
                url:'http://localhost:8085/product/selectAll',
                params: {
                    name:this.product.search.name,
                    introduction:this.product.search.introduction,
                    author:this.product.search.author,
                    type:this.product.search.point,
                    style:this.product.search.style,
                    page:this.product.currentPage,
                    size:10
                }
            }).then(response => { //返回列表清单
                if (response.status == 200){
                    this.product.data = response.data.content,
                    this.product.total = response.data.totalPages
                }
            });
        },
        LogIn(){ //登录
            this.$refs['user'].validate((valid) => {
                if (valid) {
                     this.axios({
                        method:'get',
                        url:'http://localhost:8085/user/checkUser',
                        params: {
                            name:this.user.updateName,
                            password:this.user.password,
                        }
                    }).then(response => { //返回列表清单
                        if (response.status == 200){
                            if(response.data == true){ //登陆验证，成功同时向session添加数据
                                this.$session.set('userName',this.user.updateName)
                                this.$alert('登录成功！');
                                this.$router.go(0)  //刷新页面
                            }
                            else{ //验证失败则登录失败
                                this.$alert('用户名或密码错误！');
                            }
                        }
                    });    
                } else {
                    return false;
                }
            });
        },
        Register(){ //注册
            this.$refs['user'].validate((valid) => {
                if (valid) {
                    this.axios({
                        method:'get',
                        url:'http://localhost:8085/user/checkByUserName',
                        params: {
                            name:this.user.updateName,
                        }
                    }).then(response => { //返回列表清单
                        if (response.status == 200){
                            if(response.data == false){ //验证用户名不重复，则注册成功
                                this.axios({
                                    method:'get',
                                    url:'http://localhost:8085/user/insertOne',
                                    params: {
                                        name:this.user.updateName,
                                        password:this.user.password,
                                    }
                                });
                                this.$alert('注册成功！');
                            }
                            else{  //重复则注册失败
                                this.$alert('用户已经存在！');
                            }
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        //跳转：        
        GotoUser(){//用户管理跳转
            if(this.user.name == '未登录'){//如果是未登录，就打开登录和注册弹窗
                this.user.dialogFormVisible = true
            }
            else{ //如果已经登录，就跳转到用户管理
                this.$router.push({name: 'User'})
            }
        },
        GotoProduct(data){//作品评测页跳转
            this.$router.push({
                name: 'Product',
                query: data//将作品信息传到显示页面去
            })
        },
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
</style>