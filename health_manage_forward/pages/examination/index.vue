<template>
  <!-- v-if="logined" -->
  <v-layout v-if="logined" column align-center justify-center>
    <v-toolbar color="cyan accent-3" width="100%">
      <v-btn icon disabled> </v-btn>
      <v-btn icon disabled> </v-btn>
      <v-toolbar-title>健康监测-体检管理</v-toolbar-title>
    </v-toolbar>
    <p></p>
    <v-card width="90%">
      <v-card-title>
        各项监测数据显示
      </v-card-title>
      <v-divider />
      <v-card-subtitle>
        点击【
        <code>各项指标</code>
        】可以了解各指标详细变化趋势。可以填最新的指标数据。
      </v-card-subtitle>
      <p></p>
      <v-data-table :headers="headers" :items="items" :loading="loading" class="elevation-1" hide-default-footer disable-sort>
        <template v-slot:header.sbp="{ header }">
          <a :href="'/examination/item/' + header.value">
            {{ header.text }}
          </a>
        </template>
        <template v-slot:header.dpp="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.bmi="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.whr="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.bun="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.ua="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.crea="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.tg="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.chol="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.hdl="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.ldl="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.glu="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.hcy="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.mAlb="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
        <template v-slot:header.mAlbCrea="{ header }">
          <a :href="'/examination/item/' + header.value">{{ header.text }}</a>
        </template>
      </v-data-table>
      <v-card-actions>
        <v-spacer />
        <el-upload :on-change="handleChange" :on-remove="handleRemove" :on-exceed="handleExceed" :before-remove="beforeRemove" :limit="limitUpload" :auto-upload="false" class="upload-demo" action="" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">
          <el-button size="small" type="primary">
            外部导入
          </el-button>
        </el-upload>
        <v-spacer />
      </v-card-actions>
    </v-card>
    <p></p>
  </v-layout>
</template>

<script>
import { getToken } from "../../api/auth";
import { findAllByUid, addFromExcel, deleteExcelData } from "../../api/item/exam";
import { formatData, switchTime } from "../../api/common";
export default {
  data: () => ({
    headers: [
      {
        text: "体检时间",
        align: "start",
        value: "examedtime"
      },
      { text: "收缩压(mmHg)", value: "sbp" },
      { text: "舒张压(mmHg)", value: "dpp" },
      { text: "体重指数(BMI)", value: "bmi" },
      { text: "腰臀比", value: "whr" },
      { text: "胆固醇(mmol/L)", value: "bun" },
      { text: "尿素(mg/dl)", value: "ua" },
      { text: "尿酸(μmmol/L)", value: "crea" },
      { text: "肌酐(μmmol/L)", value: "tg" },
      { text: "甘油三酯(mmol/L)", value: "chol" },
      { text: "高密度脂蛋白(mmol/L)", value: "hdl" },
      { text: "低密度脂蛋白(mmol/L)", value: "ldl" },
      { text: "葡萄糖(mmol/L)", value: "glu" },
      { text: "同型半膀胱酸(μmmol/L)", value: "hcy" },
      { text: "微量血蛋白(g/dl)", value: "mAlb" },
      { text: "微白/尿肌酐(ug/mg)", value: "mAlbCrea" }
    ],
    items: [],
    loading: true,
    logined: false,
    user: "",
    limitUpload: 1,
    fileTemp: null,
    file: null,
    da: [],
    outdata: []
  }),
  created () {
    if (getToken()) {
      console.log(getToken());
      this.logined = true;
      this.getItems();
    } else {
      this.$message.error("你还未登录，请先登录");
      this.logined = false;
      setTimeout(() => {
        this.$router.push("/login");
      }, 2000);
    }
  },
  methods: {
    async getItems () {
      await this.$store
        .dispatch("GetUserInfo")
        .then(resp => {
          this.user = resp.data;
        })
        .catch(err => {
          console.log("err: " + err);
        });
      await findAllByUid(this.user.id).then(resp => {
        if (resp.status !== 200) {
          this.$message.error("搜索体检表失败");
        }
        resp.data.forEach(el => {
          el.examedtime = formatData(el.examedtime).substring(0, 10);
        });
        this.items = resp.data;
        this.loading = false;
      });
    },
    // 上传文件时处理方法
    handleChange (file, fileList) {
      this.fileTemp = file.raw;
      if (this.fileTemp) {
        if ((this.fileTemp.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') ||
          (this.fileTemp.type === 'application/vnd.ms-excel')) {
          this.importfxx(this.fileTemp);
        } else {
          this.$message({
            type: "warning",
            message: "附件格式错误,请删除后重新上传！"
          })
        }
      } else {
        this.$message({
          type: "warning",
          message: "请上传附件!"
        })
      }
    },
    // 超出最大文件上传数量时的处理方法
    handleExceed () {
      this.$message({
        type: "warning",
        message: "超出最大上传文件数量的限制,请先删除当前已上传的文件"
      })
    },
    // 删除文件时的操作
    handleRemove () {
      this.fileTemp = null;
      this.da = []
      const examedtimes = []
      this.outdata.map(v => {
        const date = new Date(switchTime(v.examedtime, '-'));
        // const testDate = new Date();
        // console.log(date.getFullYear())
        // console.log(date.getMonth());
        // console.log(date.getDate())
        // testDate.setFullYear(date.getFullYear());
        // testDate.setMonth(date.getMonth() + 1);
        // testDate.setDate(date.getDate() - 1);
        const result = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + (date.getDate() - 1);
        examedtimes.push(result);
      })
      console.log(examedtimes);
      deleteExcelData(examedtimes.join())
        .then(resp => {
          if (resp.status === 200) {
            this.getItems();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        })
    },
    // 移除文件前的操作方法
    beforeRemove () {
      return this.$confirm('此操作将清除该文件所包含数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      });
    },
    importfxx (obj) {
      const _this = this;
      _this.outdata = [];
      // const inputDOM = this.$refs.inputer;
      // 通过DOM取文件数据
      this.file = event.currentTarget.files[0];
      const rABS = false;
      const f = this.file;
      const reader = new FileReader();
      FileReader.prototype.readAsBinaryString = function (f) {
        let binary = "";
        const rABS = false;// 是否将文件读取为二进制字符串
        // const pt = this;
        let wb; // 读取完成的数据
        let outdata;
        const reader = new FileReader();
        reader.onload = function (e) {
          const bytes = new Uint8Array(reader.result);
          const length = bytes.byteLength;
          for (let i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i]);
          }
          const XLSX = require("xlsx");
          if (rABS) {
            wb = XLSX.read(btoa(this.fixdata(binary)), {
              // 手动转化
              type: "base64"
            });
          } else {
            wb = XLSX.read(binary, {
              type: "binary"
            });
          }
          outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
          // outdata就是你想要的数据
          console.log('未处理的原始数据如下：');
          console.log(outdata);
          // const arr = [];
          _this.outdata = outdata;
          _this.da = [];
          outdata.map(v => {
            const obj = {}
            console.log(switchTime(v.examedtime, '-'));
            console.log(new Date(switchTime(v.examedtime, '-')))
            obj.examedtime = new Date(switchTime(v.examedtime, '-'))
            obj.sbp = v.sbp
            obj.dpp = v.dpp
            obj.bmi = v.bmi
            obj.whr = v.whr
            obj.bun = v.bun
            obj.ua = v.ua
            obj.crea = v.crea
            obj.tg = v.tg
            obj.chol = v.chol
            obj.hdl = v.hdl
            obj.ldl = v.ldl
            obj.glu = v.glu
            obj.hcy = v.hcy
            obj.mAlb = v.mAlb
            obj.mAlbCrea = v.mAlbCrea
            obj.userId = _this.user.id;
            _this.da.push(obj);
            // console.log(_this.da)
            // arr.push(obj)
          });
          console.log(_this.da);
          addFromExcel(JSON.stringify(_this.da))// JSON.stringify转化后的时间已经比实际时间慢了8个小时。
            .then(resp => {
              if (resp.status === 200) {
                _this.$message.success("添加成功!");
                _this.getItems();
              }
            }).catch(err => {
              _this.$message.error("添加失败,请稍后尝试!");
              console.log(err);
            })
          // _this.da = arr;
          // _this.dalen = arr.length;
        }
        reader.readAsArrayBuffer(f);
      };
      if (rABS) {
        reader.readAsArrayBuffer(f);
      } else {
        reader.readAsBinaryString(f)
      }
    },
    fixdata (data) { // 文件流转BinaryString
      let o = "";
      let i = 0;
      const w = 10240;
      for (; i < data.byteLength / w; ++i) {
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(i * w, i * w + w)));
      }
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(i * w)));
      return o;
    }
  }
};
</script>

<style scoped></style>
