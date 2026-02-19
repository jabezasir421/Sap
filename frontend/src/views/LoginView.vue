<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();
const token = ref("");
const tenantId = ref("");

function save() {
  if (!token.value || !tenantId.value) {
    return;
  }

  authStore.save(token.value, tenantId.value);
  router.push("/modules");
}
</script>

<template>
  <section class="card login-card">
    <h2>Sign in with OAuth access token</h2>
    <p class="hint">Paste a valid JWT and tenant id claim value.</p>

    <label>
      Access Token
      <textarea v-model="token" rows="6" placeholder="eyJ..." />
    </label>

    <label>
      Tenant Id
      <input v-model="tenantId" type="text" placeholder="tenant-a" />
    </label>

    <button @click="save">Continue</button>
  </section>
</template>
