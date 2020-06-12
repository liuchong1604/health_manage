<template>
  <quill-editor ref="myTextEditor" v-model="content" :options="editorOption" @change="onChange()">
    <v-container id="toolbar" slot="toolbar">
      <select class="ql-size">
        <option value="small"></option>
        <!-- Note a missing, thus falsy value, is used to reset to default -->
        <option selected></option>
        <option value="large"></option>
        <option value="huge"></option>
      </select>
      <select class="ql-align">
        <option selected=""></option>
        <option value="center"></option>
        <option value="right"></option>
        <option value="justify"></option>
      </select>
      <span class="ql-formats">
        <button class="ql-header" value="1"></button>
        <button class="ql-header" value="2"></button>
        <button class="ql-bold"></button>
        <button class="ql-italic"></button>
      </span>
      <span class="ql-formats">
        <button class="ql-script" value="sub"></button>
        <button class="ql-script" value="super"></button>
      </span>
      <span class="ql-formats">
        <button type="button" class="ql-blockquote"></button>
      </span>
      <span class="ql-formats">
        <button class="ql-list" value="ordered"></button>
      </span>
      <span class="ql-formats">
        <button class="ql-link"></button>
      </span>
      <span class="ql-formats">
        <button @click="imgClick" style="outline:none">
          <svg viewBox="0 0 18 18">
            <rect class="ql-stroke" height="10" width="12" x="3" y="4"></rect>
            <circle class="ql-fill" cx="6" cy="7" r="1"></circle>
            <polyline class="ql-even ql-fill" points="5 12 5 11 7 9 8 10 11 7 13 9 13 12 5 12"></polyline>
          </svg>
        </button>
      </span>
      <span class="ql-formats">
        <button class="ql-video"></button>
      </span>
    </v-container>
  </quill-editor>
</template>

<script>
import { uploadImage } from "../api/common";
export default {
  props: {
    value: {
      type: String,
      default: ""
    },
    // 上传图片地址
    uploadUrl: {
      type: String,
      default: "/"
    },
    fileName: {
      type: String,
      default: "file"
    },
    maxUploadSize: {
      type: Number,
      default: 1024 * 1024 * 500
    }
  },
  data: () => ({
    content: "",
    editorOption: {
      placeholder: "请输入编辑内容",
      theme: "snow", // 主题风格,
      modules: {
        toolbar: "#toolbar"
      }
    }
  }),
  computed: {
    editor () {
      return this.$refs.myTextEditor.quill;
    }
  },
  methods: {
    onChange () {
      // this.$emit("toggle", this.content);
      this.$emit("input", this.content);
    },
    /* 选择上传图片切换 */
    onFileChange (e) {
      const fileInput = e.target;
      if (fileInput.files.length === 0) {
        return;
      }
      this.editor.focus();
      if (fileInput.files[0].size > this.maxUploadSize) {
        this.$alert("图片不能大于500KB", "图片尺寸过大", {
          confirmButtonText: "确定",
          type: "warning"
        });
      }
      const data = new FormData();
      data.append(this.fileName, fileInput.files[0]);
      uploadImage(this.uploadUrl, data).then(res => {
        console.log(res.data);
        console.log(this.editor.getSelection().index);
        if (res.status === 201) {
          // insertEmbed(index: Number(插入的位置), type: String(标签类型), value: any(参数，将传入到create的方法中去), source: String = 'api')
          this.editor.insertEmbed(
            // 此方法为插入图片
            this.editor.getSelection().index, // 此表达式为光标目前所在位置// 插入链接示例: quill.insertEmbed(length, 'link', {href:res, innerText:file.name}, "api")
            "image",
            res.data
          );
          this.editor.setSelection(this.editor.getSelection().index + 1); // 光标位置后移一位
        }
      });
    },
    /* 点击上传图片按钮 */
    imgClick () {
      if (!this.uploadUrl) {
        console.log("no editor uploadUrl");
        return;
      }
      /* 内存创建input file */
      const input = document.createElement("input");
      input.type = "file";
      input.name = this.fileName;
      input.accept = "image/jpeg,image/png,image/jpg,image/gif";
      input.onchange = this.onFileChange;
      input.click(); // 弹出文件弹出框
    }
  }
};
</script>

<style scoped></style>
