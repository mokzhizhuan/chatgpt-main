import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/Home.vue";
import AboutView from "../views/About.vue";
import NotFound from "../views/NotFound.vue";
import UnAuthorized from "../views/UnAuthorized.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/chat",
    name: "chat",
    component: HomeView,
  },
  {
    path: "/about",
    name: "About",
    component: AboutView,
  },
  {
    path: "/unauthorized",
    name: "UnAuthorized",
    component: UnAuthorized,
  },
  {
    path: "/notfound",
    name: "notfound",
    component: NotFound,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
