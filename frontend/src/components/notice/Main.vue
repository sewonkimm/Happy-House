<template>
  <div class="main">
    <h1 class="title">{{ text }}</h1>

    <v-app class="v-app">
      <div class="buttonContainer">
        <v-btn
          v-if="getAccessToken"
          depressed
          large
          color="#fedccc"
          @click="showEditor"
          class="insert"
        >글 작성</v-btn>

        <div class="search">
          <input type="text" v-model="searchWord" />
          <v-icon large color="#203eac" @click="searchNotice">{{ svgPath }}</v-icon>
        </div>
      </div>

      <!-- 글 작성 modal start -->
      <v-row v-if="dialog" data-app="true">
        <v-dialog v-model="dialog" persistent max-width="1200">
          <v-card>
            <v-card-title class="headline">글 작성</v-card-title>
            <v-card-text>
              <v-text-field placeholder="제목" v-model="noticeTitle" outlined></v-text-field>
              <editor
                :initialValue="editorText"
                :options="editorOptions"
                height="500px"
                initialEditType="wysiwyg"
                previewStyle="vertical"
                ref="toastuiEditor"
              />
            </v-card-text>

            <!-- test -->
            <!-- new for file upload -->
            <div>
              <input type="checkbox" id="fileUploadFlag" v-model="uploadToggle" />
              <label for="fileUploadFlag">썸네일 이미지 추가</label>
            </div>

            <div v-show="uploadToggle" class="uploadContainer">
              <input type="file" ref="fileImage" id="fileImage" @change="selectFile" />
              <img v-if="fileUrl" :src="fileUrl" class="uploadImage" />
            </div>
            <!-- /new for file upload -->

            <v-card-actions>
              <v-spacer class="editorButtonContainer">
                <v-btn depressed color="secondary" elevation="2" @click="closeEditor">닫기</v-btn>
                <v-btn depressed color="primary" elevation="2" @click="insertNotice">등록</v-btn>
              </v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
      <!-- 글 작성 modal end -->

      <div class="boards">
        <Board
          v-for="(board, index) in boardList"
          :key="index"
          :board="board"
          @get-list="getList(1)"
        />
        <v-pagination v-model="page" :length="totalPage" @input="getList" />
      </div>
    </v-app>
  </div>
</template>

<script>
import Board from "@/components/notice/Board.vue";
import axios from "axios";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

import { mdiMagnify } from "@mdi/js";
import { mapGetters } from "vuex";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "NoticeMain",
  data() {
    return {
      text: "공지사항",
      svgPath: mdiMagnify,
      noticeTitle: "",
      editorText: "",
      // vuetify
      dialog: false,
      editorOptions: {
        hideModeSwitch: false
      },
      // BoardList
      searchWord: "",
      boardList: [],
      // Pagination
      page: 1,
      totalPage: 3,
      totalNoticeCount: 0,
      pageLimit: 3,
      // FileUpload
      uploadToggle: false,
      uploadFile: null,
      fileUrl: ""
    };
  },
  created: function() {
    this.getList(1);
  },
  computed: {
    ...mapGetters(["getAccessToken", "getUserSeq"])
  },
  methods: {
    showEditor: function() {
      this.dialog = !this.dialog;
    },
    closeEditor: function() {
      this.dialog = false;
      this.uploadToggle = false;
      this.fileUrl = "";
      this.uploadFile = null;

      this.noticeTitle = "";
      this.editorText = "";
      this.$refs.toastuiEditor.invoke("setHtml", this.editorText);
    },
    insertNotice: function() {
      const formData = new FormData();
      formData.append("title", this.noticeTitle);
      formData.append("content", this.$refs.toastuiEditor.invoke("getHtml"));
      formData.append("userSeq", this.getUserSeq);
      formData.append("file", this.uploadFile);

      axios
        .post(SERVER_URL + "/notice", formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          this.closeEditor();
          this.page = 1;

          // 서버에 파일 업로드 되었지만 호출이 업로드보다 먼저 되어서 timeout 처리
          setTimeout(() => {
            this.getList(1);
          }, 2000);
        })
        .catch(error => {
          console.log(error);
          this.closeEditor();
        });
    },
    getList: function(page) {
      this.page = page;
      this.boardList = [];

      axios
        .get(SERVER_URL + "/notice", {
          params: {
            limit: this.pageLimit,
            offset: this.pageLimit * (page - 1)
          }
        })
        .then(response => {
          this.boardList = response.data.list;
          this.totalPage = Math.ceil(response.data.count / this.pageLimit);

          this.searchWord = "";
          this.editorText = "";
        })
        .catch(error => {
          console.log(error);
          this.closeEditor();
        });
    },
    searchNotice: function() {
      this.boardList = [];
      this.page = 1;
      axios
        .get(SERVER_URL + "/notice", {
          params: {
            limit: this.pageLimit,
            offset: this.pageLimit * (this.page - 1),
            searchWord: this.searchWord
          }
        })
        .then(response => {
          this.boardList = response.data.list;
          this.totalPage = Math.ceil(response.data.count / this.pageLimit);
        })
        .catch(error => {
          console.log(error);
          this.closeEditor();
        });
    },
    selectFile: function() {
      let file = this.$refs.fileImage.files[0];
      this.uploadFile = file;
      this.fileUrl = window.URL.createObjectURL(file);
    }
  },
  components: {
    Board,
    editor: Editor
  }
};
</script>

<style scoped src="@/assets/css/notice/Main.css" />
