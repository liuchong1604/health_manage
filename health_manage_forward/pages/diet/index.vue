<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>膳食推荐</v-toolbar-title>
    </v-toolbar>
    <p></p>
    <v-card width="80%">
      <v-card-title class="headline">饮食管理</v-card-title>
      <el-form ref="form" :model="form" :rules="formRules" label-position="right" label-width="80px" class="demo-ruleForm">
        <el-form-item prop="heat">
          <el-col :span="9">
            <el-tooltip content="此数值可以从工具界面计算得出" placement="top" effect="light">
              <el-input v-model="form.heat" placeholder="请输入你当前需要的热量值" size="small">
                <template slot="prepend">热量值</template>
                <template slot="append">千卡</template>
              </el-input>
            </el-tooltip>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button @click="generate" type="primary">生成食谱</el-button>
        </el-form-item>
      </el-form>
      <v-divider />
      <v-row>
        <v-col md="5"></v-col>
        <v-col md="3">
          <v-card-text>
            <p class="title">
              推荐配餐
            </p>
          </v-card-text>
        </v-col>
        <v-col md="4"></v-col>
      </v-row>

      <el-col :span="22" :offset="1">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="date" label="时间" width="150" align="center" fixed>
          </el-table-column>
          <el-table-column label="早餐" align="center">
            <el-table-column prop="breakfast.b1" label="1" width="120" align="center">
            </el-table-column>
            <el-table-column prop="breakfast.b2" label="2" width="120" align="center">
            </el-table-column>
            <el-table-column prop="breakfast.b3" label="3" width="120" align="center">
            </el-table-column>
            <el-table-column prop="breakfast.b4" label="4" width="120" align="center">
            </el-table-column>
          </el-table-column>
          <el-table-column label="午餐" align="center">
            <el-table-column prop="launch.l1" label="1" width="120" align="center">
            </el-table-column>
            <el-table-column prop="launch.l2" label="2" width="120" align="center">
            </el-table-column>
            <el-table-column prop="launch.l3" label="3" width="120" align="center">
            </el-table-column>
            <el-table-column prop="launch.l4" label="4" width="120" align="center">
            </el-table-column>
          </el-table-column>
          <el-table-column label="晚餐" align="center">
            <el-table-column prop="dinner.d1" label="1" width="120" align="center">
            </el-table-column>
            <el-table-column prop="dinner.d2" label="2" width="120" align="center">
            </el-table-column>
            <el-table-column prop="dinner.d3" label="3" width="120" align="center">
            </el-table-column>
            <el-table-column prop="dinner.d4" label="4" width="120" align="center">
            </el-table-column>
          </el-table-column>
        </el-table>
      </el-col>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { generateDiet } from "../../api/item/diet";
export default {
  data: () => ({
    form: {
      heat: ""
    },
    formRules: {
      heat: [{ required: true, message: "所需热量值不能为空", trigger: "blur" }]
    },
    tableData: []
  }),
  methods: {
    generate () {
      this.$refs.form.validate(valid => {
        if (valid) {
          const formData = {
            heat: this.form.heat
          };
          const arr = [];
          generateDiet(this.$qs.stringify(formData))
            .then(resp => {
              this.$message({
                message: "恭喜你，成功获取食谱",
                type: "success"
              });
              console.log(resp.data);
              console.log(resp.data[0][0][0]);
              for (const el in resp.data) {
                if (el === "0") {
                  arr.push({ date: "周一" });
                } else if (el === "1") {
                  arr.push({ date: "周二" });
                } else if (el === "2") {
                  arr.push({ date: "周三" });
                } else if (el === "3") {
                  arr.push({ date: "周四" });
                } else if (el === "4") {
                  arr.push({ date: "周五" });
                } else if (el === "5") {
                  arr.push({ date: "周六" });
                } else if (el === "6") {
                  arr.push({ date: "周日" });
                }
              }
              for (let i = 0; i < 7; i++) {
                for (let j = 0; j < 3; j++) {
                  if (j === 0) {
                    arr[i].breakfast = {};
                  } else if (j === 1) {
                    arr[i].launch = {};
                  } else if (j === 2) {
                    arr[i].dinner = {};
                  }
                  for (let k = 0; k < 4; k++) {
                    if (j === 0) {
                      if (k === 0) {
                        arr[i].breakfast.b1 = resp.data[i][j][k];
                      } else if (k === 1) {
                        arr[i].breakfast.b2 = resp.data[i][j][k];
                      } else if (k === 2) {
                        arr[i].breakfast.b3 = resp.data[i][j][k];
                      } else if (k === 3) {
                        arr[i].breakfast.b4 = resp.data[i][j][k];
                      }
                    } else if (j === 1) {
                      if (k === 0) {
                        arr[i].launch.l1 = resp.data[i][j][k];
                      } else if (k === 1) {
                        arr[i].launch.l2 = resp.data[i][j][k];
                      } else if (k === 2) {
                        arr[i].launch.l3 = resp.data[i][j][k];
                      } else if (k === 3) {
                        arr[i].launch.l4 = resp.data[i][j][k];
                      }
                    } else if (j === 2) {
                      if (k === 0) {
                        arr[i].dinner.d1 = resp.data[i][j][k];
                      } else if (k === 1) {
                        arr[i].dinner.d2 = resp.data[i][j][k];
                      } else if (k === 2) {
                        arr[i].dinner.d3 = resp.data[i][j][k];
                      } else if (k === 3) {
                        arr[i].dinner.d4 = resp.data[i][j][k];
                      }
                    }
                  }
                }
              }
              this.tableData = arr;
            })
            .catch(err => {
              console.log("err: " + err);
            });
        }
      });
    }
  }
};
</script>

<style scoped></style>
