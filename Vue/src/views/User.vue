<template>
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container>  
        <el-header>
            <el-container>
                用户管理

            </el-container>
        </el-header>
        <el-main>
            <el-tabs v-model="tab" type="card">
                <el-tab-pane label="用户信息查看与修改" name="first">
                    
                </el-tab-pane>
                <el-tab-pane label="" name="second">

                </el-tab-pane>
                <el-tab-pane label="用户推荐" name="third">

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
      }
    },
    mounted(){ //初始化自动执行工作


    },
    methods: {

    }
}
</script>