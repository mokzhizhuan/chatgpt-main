<template>
  <div>
    <div class="container my-3">
      <div id="loading" class="text-bg-primary text-center rounded"></div>
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
          method="post"
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
      formdata: new Chatmessage(),
      loading: false,
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
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err.response);
      });
  },
  methods: {},
};
</script>
