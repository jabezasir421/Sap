import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import ModulesView from "@/views/ModulesView.vue";
import ProjectsView from "@/views/ProjectsView.vue";

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/modules" },
    { path: "/login", component: LoginView },
    { path: "/modules", component: ModulesView },
    { path: "/projects", component: ProjectsView }
  ]
});

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem("access_token");
  if (to.path !== "/login" && !token) {
    return next("/login");
  }
  next();
});
