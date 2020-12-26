<template>
  <div>
    <div class="board" @click="showDetail">
      <div class="boardLeft">
        <img
          v-if="defaultImage"
          src="@/assets/images/main_house.jpg"
          alt="thumbnail"
          class="thumbnail"
        />
        <img v-else :src="boardImage" alt="thumbnail" class="thumbnail" />
      </div>

      <div class="boardRight">
        <p class="date">{{ board.regDt.replace('T', ' ') }}</p>
        <h5 class="title">{{ board.title }}</h5>
        <Viewer :initialValue="board.content" class="content" />
      </div>
    </div>

    <!-- detail modal start -->
    <v-row data-app="true">
      <v-dialog v-model="dialog" persistent max-width="1200">
        <v-card>
          <v-card-title class="headline">{{ board.title }}</v-card-title>
          <v-card-text>
            <Viewer :initialValue="board.content" height="500px" />
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="secondary" depressed @click="closeDetail">닫기</v-btn>
            <v-btn color="primary" depressed @click="openEditor">수정</v-btn>
            <v-btn color="error" depressed @click="deleteNotice">삭제</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- detail modal end -->

      <!-- 수정 modal start -->
      <v-dialog v-model="editor" persistent max-width="1200">
        <v-card>
          <v-card-title class="headline">
            <v-text-field placeholder="제목" v-model="noticeTitle" outlined></v-text-field>
          </v-card-title>
          <v-card-text>
            <Editor
              :initialValue="editorText"
              :options="editorOptions"
              height="500px"
              initialEditType="wysiwyg"
              previewStyle="vertical"
              ref="toastuiEditor"
            />
          </v-card-text>
          <v-card-actions>
            <v-spacer class="buttonContainer">
              <v-btn depressed color="secondary" elevation="2" @click="closeEditor">닫기</v-btn>
              <v-btn depressed color="primary" elevation="2" @click="updateNotice">수정</v-btn>
            </v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- 수정 modal end -->
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor, Viewer } from "@toast-ui/vue-editor";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "Board",
  data() {
    return {
      //modal
      dialog: false,
      //editor modal
      editor: false,
      // Editor
      editorOptions: {
        hideModeSwitch: false
      },
      noticeTitle: "",
      editorText: "",
      uploadFile: null,
      defaultImage: this.board.fileList[0] !== undefined ? false : true,
      boardImage:
        this.board.fileList[0] !== undefined
          ? "/" + this.board.fileList[0].fileUrl
          : "@/assets/images/main_house.jpg"
    };
  },
  props: {
    board: Object
  },
  created: function() {
    this.noticeTitle = this.board.title;
  },
  methods: {
    showDetail: function() {
      this.dialog = true;
    },
    closeDetail: function() {
      this.dialog = false;
    },
    openEditor: function() {
      this.editorText = this.board.content;
      this.editor = !this.editor;
    },
    closeEditor: function() {
      this.editor = false;
    },
    updateNotice: function() {
      let params = new URLSearchParams();
      params.append("title", this.noticeTitle);
      params.append("content", this.$refs.toastuiEditor.invoke("getHtml"));
      axios
        .post(SERVER_URL + "/notice/" + this.board.boardId, params)
        .then(response => {
          console.log("글 수정 성공!");
          this.$emit("get-list"); // Main.vue에서 getList 호출
        })
        .catch(error => {
          console.log(error);
        });

      this.closeEditor();
      this.closeDetail();
    },
    deleteNotice: function() {
      axios
        .delete(SERVER_URL + "/notice/" + this.board.boardId)
        .then(response => {
          console.log("글 삭제 성공!");
          console.log(response);
          this.$emit("get-list"); // Main.vue에서 getList 호출
        })
        .catch(error => {
          console.log(error);
        });
      this.closeDetail();
    }
  },
  components: {
    Editor,
    Viewer
  }
};
</script>

<style scoped src="@/assets/css/notice/Board.css" />
