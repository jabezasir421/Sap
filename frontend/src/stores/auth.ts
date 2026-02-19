import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("access_token") ?? "",
    tenantId: localStorage.getItem("tenant_id") ?? ""
  }),
  actions: {
    save(token: string, tenantId: string) {
      this.token = token;
      this.tenantId = tenantId;
      localStorage.setItem("access_token", token);
      localStorage.setItem("tenant_id", tenantId);
    }
  }
});
