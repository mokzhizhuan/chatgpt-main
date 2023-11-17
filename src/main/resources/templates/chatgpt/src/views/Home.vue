<template>
  <div>
    <div class="container my-3">
      <div
        id="loading"
        class="text-bg-primary text-center rounded"
        ref="load"
        v-show="loader"
      ></div>
      <div
        id="chatGPTConversation"
        class="row my-3 border border-info rounded overflow-auto bg-dark"
      ></div>
      <div class="row alert alert-info border border-info">
        <h2 class="text-center text-primary">Neo ChatGPT Chatbot</h2>
        <form
          id="chatGPTForm"
          class="form-ai"
          encType="multipart/form-data"
          v-on:submit.prevent="handleoutput"
          method="post"
        >
          <h6 class="m-3" id="title"></h6>
          <div class="form-floating mx-3">
            <textarea
              class="form-control border border-info"
              name="prompt"
              placeholder="Enter your question"
              id="prompt"
              style="height: 100px"
              v-model="formdata.prompt"
              required
            ></textarea>
            <label for="prompt">Enter your question</label>
          </div>
          <br />
          <div class="text-center">
            <button
              id="submitChatGPT"
              class="btn btn-lg btn-primary text-center"
              type="submit"
            >
              Chat!
            </button>
          </div>
        </form>
      </div>
    </div>
    <footer>
      <div class="container text-white fw-bold">
        <p align="center">© 2023 by Neo Wolf</p>
      </div>
    </footer>
  </div>
</template>

<script>
import Chatmessage from "../model/chatgptbean";
import { BASE_API_URL } from "../common/Constants";
import axios from "axios";
const API_URL = BASE_API_URL + "/api/V1/";
axios.defaults.baseURL = API_URL;

export default (await import("vue")).defineComponent({
  name: "HomeView",
  data() {
    return {
      chatgptbean: [],
      chatbot: false,
      formdata: new Chatmessage(),
      loading: false,
      loader: false,
      submitted: false,
      errorMessage: "",
      answer: "",
    };
  },
  mounted() {
    axios
      .get(API_URL)
      .then((response) => {
        this.chatgptbean = response.data;
        document.getElementById("title").innerHTML = this.chatgptbean.title;
      })
      .catch((err) => {
        console.log(err.response);
      });
  },
  methods: {
    handleoutput() {
      if (!this.formdata.prompt) {
        return;
      }
      this.chatgptbean.prompt = this.formdata.prompt;
      axios
        .postForm(API_URL, this.chatgptbean)
        .then((response) => {
          console.log(response);
          this.loading = true;
          var vm = this;
          vm.showWait(); //show loaders
          document.getElementById("chatGPTConversation").innerHTML =
            '<code class="text-justify d-flex flex-lg-wrap chat"><strong class="small" style="font-size: 24px;">' +
            response.data +
            '</strong></code>"'; //answer
          vm.hideWait(); //hider loader
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showWait() {
      this.loader = true; //loading
      this.$refs.load.innerHTML = "Processing.  Please wait ...";
      this.formdata.prompt = null; //empty formdata
    },
    hideWait() {
      this.loader = false;
    },
  },
});
</script>
