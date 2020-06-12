<template>
  <v-container>
    <v-row dense>
      <v-col md="1"></v-col>
      <v-col md="6">
        <v-card width="900">
          <div id="myChart" style="width: 600px;height:400px;"></div>
        </v-card>
        <v-card v-if="path === 'bmi'" width="900">
          <v-card-title>中国人体重判断标准</v-card-title>
          <v-simple-table height="300px">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">消瘦</th>
                  <th class="text-left">正常</th>
                  <th class="text-left">超重</th>
                  <th class="text-left">肥胖</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>BMI＜18.5</td>
                  <td>18.5≦BMI＜24</td>
                  <td>24≦BMI＜28</td>
                  <td>BMI≧28</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
        <v-card v-if="path === 'whr'" width="900">
          <v-card-title>中心性肥胖的标准</v-card-title>
          <v-simple-table height="300px">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">男性</th>
                  <th class="text-left">女性</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>≧85厘米（≧两尺六）</td>
                  <td>≧80厘米（≧两尺四）</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-col>
      <v-col md="5">
        <v-card v-if="path === 'bmi'" width="500">
          <v-card-title>身高(cm)、体重(kg)</v-card-title>
          <v-divider />
          <el-form
            ref="form"
            :model="BMIForm"
            :rules="BMIRules"
            size="medium"
            label-position="left"
            label-width="60px"
          >
            <el-date-picker v-model="value1" type="date" placeholder="选择日期">
              <p></p>
            </el-date-picker>
            <el-form-item label="体重(kg)" prop="weight">
              <el-col :span="8">
                <el-input
                  v-model.number="BMIForm.weight"
                  placeholder="50-150"
                  autocomplete="off"
                >
                  <template slot="append">kg</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="身高(cm)" prop="weight">
              <el-col :span="8">
                <el-input
                  v-model.number="BMIForm.height"
                  placeholder="100-200"
                  autocomplete="off"
                >
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="体重指数" prop="BMI">
              <el-col :span="8">
                <el-input
                  v-model.number="BMIForm.BMI"
                  autocomplete="off"
                  disabled
                >
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
        </v-card>
        <v-card v-if="path === 'whr'" width="500">
          <v-card-title>腰围(cm)</v-card-title>
          <v-divider />
          <el-form
            ref="form"
            :model="WHRForm"
            :rules="WHRRules"
            size="medium"
            label-position="left"
            label-width="60px"
          >
            <el-date-picker v-model="value2" type="date" placeholder="选择日期">
            </el-date-picker>
            <p></p>
            <el-form-item label="腰围(cm)" prop="waistline">
              <el-col :span="8">
                <el-input
                  v-model.number="WHRForm.waistline"
                  placeholder="50-100"
                  autocomplete="off"
                >
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { findByUid } from "../../../api/item/exam";
import { formatData } from "../../../api/common";
export default {
  data() {
    const checkWeight = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("体重不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 50) {
            callback(new Error("体重必须大于50kg"));
          } else if (value > 150) {
            callback(new Error("体重必须小于150kg"));
          }
          callback();
        }
      }, 1000);
    };
    const checkHeight = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("身高不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 100) {
            callback(new Error("身高必须大于1m"));
          } else if (value > 200) {
            callback(new Error("身高必须小于2m"));
          }
          callback();
        }
      }, 1000);
    };
    const checkWHR = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("腰围不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 50) {
            callback(new Error("腰围必须大于50cm"));
          } else if (value > 200) {
            callback(new Error("身高必须小于200cm"));
          }
          callback();
        }
      }, 1000);
    };
    return {
      text: "",
      data: [],
      user: "",
      path: "",
      name: "",
      legend: {},
      BMIForm: {
        weight: 60,
        height: 170,
        BMI: 0
      },
      BMIRules: {
        weight: [
          { required: true, message: "体重不能为空" },
          { validator: checkWeight, trigger: "blur" }
        ],
        height: [
          { required: true, message: "身高不能为空" },
          { validator: checkHeight, trigger: "blur" }
        ]
      },
      // isEdit: false
      value1: "",
      value2: "",
      WHRForm: {
        waistline: 60
      },
      WHRRules: {
        waistline: [
          { required: true, message: "腰围不能为空" },
          { validator: checkWHR, trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    BMI() {
      return (
        parseFloat(this.BMIForm.weight) /
        ((parseFloat(this.BMIForm.height) / 100) *
          (parseFloat(this.BMIForm.height) / 100))
      ).toFixed(2);
    }
  },
  // asyncData({isDev, route, store, env, params, query, req, res, redirect, error}) {},
  created() {
    this.getData();
  },

  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = this.$echarts.init(document.getElementById("myChart"));
      this.path = this.$route.params.id;
      let datax = []; // 横坐标参数
      let arr = []; // 具体数值

      if (this.path === "sbp") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "收缩压变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.sbp);
        });
        this.name = "收缩压";
        this.legend = { data: ["收缩压"] };
      } else if (this.path === "dpp") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "舒张压变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.dpp);
        });
        console.log(datax);
        this.name = "舒张压";
        this.legend = { data: ["舒张压"] };
      } else if (this.path === "bmi") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "体重指数变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.bmi);
        });
        this.name = "体重指数";
        this.legend = { data: ["体重指数"] };
      } else if (this.path === "whr") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "腰臀比变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.whr);
        });
        this.name = "腰臀比";
        this.legend = { data: ["腰臀比"] };
      } else if (this.path === "bun") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "胆固醇变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.bun);
        });
        this.name = "胆固醇";
        this.legend = { data: ["胆固醇"] };
      } else if (this.path === "ua") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "尿素变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.ua);
        });
        this.name = "尿素";
        this.legend = { data: ["尿素"] };
      } else if (this.path === "crea") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "尿酸变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.crea);
        });
        this.name = "尿酸";
        this.legend = { data: ["尿酸"] };
      } else if (this.path === "tg") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "肌酐变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.crea);
        });
        this.name = "肌酐";
        this.legend = { data: ["肌酐"] };
      } else if (this.path === "chol") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "甘油三酯变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.chol);
        });
        this.name = "甘油三酯";
        this.legend = { data: ["甘油三酯"] };
      } else if (this.path === "hdl") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "高密度脂蛋白变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.hdl);
        });
        this.name = "高密度脂蛋白";
        this.legend = { data: ["高密度脂蛋白"] };
      } else if (this.path === "ldl") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "低密度脂蛋白变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.ldl);
        });
        this.name = "低密度脂蛋白";
        this.legend = { data: ["低密度脂蛋白"] };
      } else if (this.path === "glu") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "葡萄糖变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.glu);
        });
        this.name = "葡萄糖";
        this.legend = { data: ["葡萄糖"] };
      } else if (this.path === "hcy") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "同型半膀胱酸变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.hcy);
        });
        this.name = "同型半膀胱酸";
        this.legend = { data: ["同型半膀胱酸"] };
      } else if (this.path === "mAlb") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "微量血蛋白变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.mAlb);
        });
        this.name = "微量血蛋白";
        this.legend = { data: ["微量血蛋白"] };
      } else if (this.path === "mAlbCrea") {
        datax = [];
        arr = [];
        this.legend = {};
        this.text = "微白/尿肌酐变化情况一览";
        this.data.forEach(el => {
          datax.push(el.examedtime);
          arr.push(el.mAlbCrea);
        });
        this.name = "微白/尿肌酐";
        this.legend = { data: ["微白/尿肌酐"] };
      }
      // 绘制图表
      myChart.setOption({
        title: { text: this.text },
        tooltip: {},
        xAxis: {
          data: datax
        },
        yAxis: {},
        series: [
          {
            name: this.name,
            type: "line",
            data: arr
          }
        ]
      });
    },
    async getData() {
      await this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.user = resp.data;
        })
        .catch(err => {
          console.log("err: " + err);
        });
      await findByUid(this.$route.params.id, this.user.id)
        .then(resp => {
          resp.data.forEach(el => {
            el.examedtime = formatData(el.examedtime).substring(0, 10);
          });
          this.data = resp.data;
          console.log(this.data);
          const myChart = this.$echarts.init(
            document.getElementById("myChart")
          );
          this.path = this.$route.params.id;
          let datax = []; // 横坐标参数
          let arr = []; // 具体数值
          if (this.path === "sbp") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "收缩压变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.sbp);
            });
            this.name = "收缩压";
            this.legend = { data: ["收缩压"] };
          } else if (this.path === "dpp") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "舒张压变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.dpp);
            });
            console.log(datax);
            this.name = "舒张压";
            this.legend = { data: ["舒张压"] };
          } else if (this.path === "bmi") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "体重指数变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.bmi);
            });
            this.name = "体重指数";
            this.legend = { data: ["体重指数"] };
          } else if (this.path === "whr") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "腰臀比变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.whr);
            });
            this.name = "腰臀比";
            this.legend = { data: ["腰臀比"] };
          } else if (this.path === "bun") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "胆固醇变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.bun);
            });
            this.name = "胆固醇";
            this.legend = { data: ["胆固醇"] };
          } else if (this.path === "ua") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "尿素变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.ua);
            });
            this.name = "尿素";
            this.legend = { data: ["尿素"] };
          } else if (this.path === "crea") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "尿酸变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.crea);
            });
            this.name = "尿酸";
            this.legend = { data: ["尿酸"] };
          } else if (this.path === "tg") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "肌酐变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.crea);
            });
            this.name = "肌酐";
            this.legend = { data: ["肌酐"] };
          } else if (this.path === "chol") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "甘油三酯变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.chol);
            });
            this.name = "甘油三酯";
            this.legend = { data: ["甘油三酯"] };
          } else if (this.path === "hdl") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "高密度脂蛋白变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.hdl);
            });
            this.name = "高密度脂蛋白";
            this.legend = { data: ["高密度脂蛋白"] };
          } else if (this.path === "ldl") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "低密度脂蛋白变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.ldl);
            });
            this.name = "低密度脂蛋白";
            this.legend = { data: ["低密度脂蛋白"] };
          } else if (this.path === "glu") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "葡萄糖变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.glu);
            });
            this.name = "葡萄糖";
            this.legend = { data: ["葡萄糖"] };
          } else if (this.path === "hcy") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "同型半膀胱酸变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.hcy);
            });
            this.name = "同型半膀胱酸";
            this.legend = { data: ["同型半膀胱酸"] };
          } else if (this.path === "mAlb") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "微量血蛋白变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.mAlb);
            });
            this.name = "微量血蛋白";
            this.legend = { data: ["微量血蛋白"] };
          } else if (this.path === "mAlbCrea") {
            datax = [];
            arr = [];
            this.legend = {};
            this.text = "微白/尿肌酐变化情况一览";
            this.data.forEach(el => {
              datax.push(el.examedtime);
              arr.push(el.mAlbCrea);
            });
            this.name = "微白/尿肌酐";
            this.legend = { data: ["微白/尿肌酐"] };
          }
          // 绘制图表
          myChart.setOption({
            title: { text: this.text },
            legend: {},
            tooltip: {},
            xAxis: {
              data: datax
            },
            yAxis: {},
            series: [
              {
                name: this.name,
                type: "line",
                data: arr
              }
            ]
          });
        })
        .catch(err => {
          console.log("err: " + err);
        });
    }
  }
};
</script>

<style scoped></style>
