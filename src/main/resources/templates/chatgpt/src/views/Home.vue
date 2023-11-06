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
        ref="chat"
        v-show="chatbot"
      ></div>
      <div class="row alert alert-info border border-info">
        <h2 class="text-center text-primary">Neo ChatGPT Chatbot</h2>
        <form
          id="chatGPTForm"
          class="form-ai"
          encType="multipart/form-data"
          v-on:submit="handleoutput"
        >
          <h6
            class="m-3"
            id="title"
            v-for="chatgpt in chatgptbean"
            :key="chatgpt"
          >
            {{ chatgpt }}
          </h6>
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

export default {
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
    };
  },
  mounted() {
    axios
      .get(API_URL)
      .then((response) => {
        this.chatgptbean = response.data;
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
      axios({
        method: "post",
        url: API_URL,
        data: {
          prompt: this.formdata.prompt,
        },
      })
        .then((response) => {
          console.log(response);
          this.chatbot = true;
          this.$refs.chat.innerHTML =
            '<code class="text-justify d-flex flex-lg-wrap chat"><strong class="small" style="font-size: 24px;">' +
            response +
            "</strong></code>";
          var vm = this;
          vm.showWait();
          this.loading = true;
        })
        .catch((error) => {
          console.log(error);
        })
        .then(() => (this.loading = false));
    },
    showWait() {
      this.loader = true;
      this.$refs.load.innerHTML =
        'Processing.  Please wait ...  <img src="images/loading.gif" alt="Processing.  Please wait ..." width="13%">';
    },
    hideWait() {
      this.loader = false;
    },
  },
};
</script>
