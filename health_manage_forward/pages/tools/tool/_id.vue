<template>
  <v-layout column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title v-if="item.id === 1">每日能量需求计算器</v-toolbar-title>
      <v-toolbar-title v-else-if="item.id === 2">
        如何减掉一公斤
      </v-toolbar-title>
      <v-toolbar-title v-else-if="item.id === 3">
        腰臀比计算器
      </v-toolbar-title>
      <v-toolbar-title v-else-if="item.id === 4">
        理想体重计算器
      </v-toolbar-title>
      <v-toolbar-title v-else-if="item.id === 5">
        体重指标(BMI)计算器
      </v-toolbar-title>
      <v-toolbar-title v-else-if="item.id === 6">
        食物营养素查看器
      </v-toolbar-title>
    </v-toolbar>

    <v-row>
      <v-col md="7">
        <v-card width="900">
          <v-card-text>
            <p class="display-1 text--primary">
              请输入你的信息
            </p>
          </v-card-text>
          <v-divider></v-divider>
          <p></p>
          <el-form
            ref="form"
            :model="energyForm"
            :rules="energyFormRules"
            v-if="item.id === 1"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item label="体重" prop="weight">
              <el-col :span="8">
                <el-input
                  v-model="energyForm.weight"
                  placeholder="请输入体重"
                  size="small"
                >
                  <template slot="append">kg</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="energyForm.gender" size="small">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-radio-group v-model="energyForm.age" size="small">
                <el-radio label="18~30岁"></el-radio>
                <el-radio label="31~60岁"></el-radio>
                <el-radio label="60岁以上"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="活动水平" prop="level">
              <el-radio-group v-model="energyForm.level" size="small">
                <el-radio label="极轻--卧床全天"></el-radio>
                <el-radio label="轻--轻活动生活模式(多做或缓步)"></el-radio>
                <el-radio label="重--一般生活度"></el-radio>
                <el-radio label="极重--活动量大的生活模式"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <el-form
            ref="form"
            :model="dietForm"
            :rules="dietFormRules"
            v-else-if="item.id === 2"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item prop="region">
              <el-select v-model="dietForm.region" size="small">
                <el-option label="生活活动" value="生活活动"></el-option>
                <el-option label="运动" value="运动"></el-option>
                <el-option label="拳击" value="拳击"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="region">
              <el-select v-model="dietForm.region1" size="small">
                <el-option
                  label="步行(80~99步/分)"
                  value="步行(80~99步/分)"
                ></el-option>
                <el-option
                  label="推儿童车和孩子一起走"
                  value="推儿童车和孩子一起走"
                ></el-option>
                <el-option label="打台球" value="打台球"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="weight">
              <el-col :span="10">
                <el-input v-model="dietForm.weight" size="small">
                  <template slot="prepend">体重</template>
                  <template slot="append">kg</template>
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
          <el-form
            ref="form"
            :model="WHRForm"
            :rules="WHRFormRules"
            v-else-if="item.id === 3"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item prop="waist">
              <el-col :span="10">
                <el-input v-model="WHRForm.waist" size="small">
                  <template slot="prepend">腰围</template>
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item prop="hipline">
              <el-col :span="10">
                <el-input v-model="WHRForm.hipline" size="small">
                  <template slot="prepend">臀围</template>
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
          <el-form
            ref="form"
            :model="WHRForm"
            :rules="WHRFormRules"
            v-else-if="item.id === 4"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item prop="waist">
              <el-col :span="10">
                <el-input v-model="WHRForm.waist" size="small">
                  <template slot="prepend">年龄</template>
                  <template slot="append">岁</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item prop="hipline">
              <el-col :span="10">
                <el-input v-model="WHRForm.hipline" size="small">
                  <template slot="prepend">身高</template>
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
          <el-form
            ref="form"
            :model="WHRForm"
            :rules="WHRFormRules"
            v-else-if="item.id === 5"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item prop="waist">
              <el-col :span="10">
                <el-input v-model="WHRForm.waist" size="small">
                  <template slot="prepend">体重</template>
                  <template slot="append">kg</template>
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item prop="hipline">
              <el-col :span="10">
                <el-input v-model="WHRForm.hipline" size="small">
                  <template slot="prepend">身高</template>
                  <template slot="append">cm</template>
                </el-input>
              </el-col>
            </el-form-item>
          </el-form>
          <el-form
            ref="form"
            :model="NutrientForm"
            :rules="NutrientFormRules"
            v-else-if="item.id === 6"
            label-position="right"
            label-width="80px"
            class="demo-ruleForm"
          >
            <el-form-item label="按名称">
              <el-col :span="10">
                <el-input v-model="NutrientForm.part1"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="按分类">
              <el-select v-model="NutrientForm.part2" placeholder="请选择">
                <el-option label="蔬菜" value="蔬菜"></el-option>
                <el-option label="水果" value="水果"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <v-card-actions>
            <v-row>
              <v-col md="1"></v-col>
              <v-col md="3">
                <v-btn class="ma-2" tile color="indigo" dark>开始计算</v-btn>
              </v-col>
              <v-col md="3">
                <v-btn class="ma-2" tile color="indigo" dark>清除重写</v-btn>
              </v-col>
            </v-row>
          </v-card-actions>
          <v-divider />
          <v-card-text>Thanks</v-card-text>
        </v-card>
        <!-- <v-card v-else-if="item.id === 2" width="900">
          <v-card-text>体重计算器</v-card-text>
        </v-card>
        <v-card v-else-if="item.id === 3" width="900">
          <v-card-text>BMI计算器</v-card-text>
        </v-card> -->
      </v-col>
      <v-col md="4">
        <v-card>
          <v-card-title>热文欣赏</v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-layout>
</template>

<script>
import { getCardById } from "../../../api/item/tools";
export default {
  data: () => ({
    valid: true,
    energyForm: {
      weight: "",
      gender: "男",
      age: "18~30岁",
      level: "极轻--卧床全天"
    },
    energyFormRules: {
      weight: [{ required: true, message: "体重不能为空", trigger: "blur" }],
      gender: [{ required: true, message: "请选择性别", trigger: "change" }],
      age: [{ required: true, message: "请选择年龄", trigger: "change" }],
      level: [{ required: true, message: "请选择活动水平", trigger: "change" }]
    },
    dietForm: {
      region: "生活活动",
      region1: "步行(80~99步/分)",
      weight: ""
    },
    dietFormRules: {
      region: [
        { required: true, message: "请选择活动水平", trigger: "change" }
      ],
      region1: [
        { required: true, message: "请选择活动水平", trigger: "change" }
      ],
      weight: [{ required: true, message: "请选择活动水平", trigger: "change" }]
    },
    WHRForm: {
      waist: "",
      hipline: ""
    },
    WHRFormRules: {
      waist: [{ required: true, message: "腰围不能为空", trigger: "blur" }],
      hipline: [{ required: true, message: "臀围不能为空", trigger: "blur" }]
    },
    NutrientForm: {
      part1: "",
      part2: ""
    },
    NutrientFormRules: {}
  }),
  asyncData({ params }) {
    return getCardById(params.id)
      .then(resp => {
        console.log(resp.data);
        return {
          item: resp.data
        };
      })
      .catch(err => {
        console.log("err: " + err);
      });
  }
};
</script>

<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 900px;
}
</style>
