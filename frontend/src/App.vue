<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

function logout() {
  localStorage.removeItem("access_token");
  localStorage.removeItem("tenant_id");
  router.push("/login");
}
</script>

<template>
  <div class="shell">
    <header class="topbar">
      <h1>ERP Core Console</h1>
      <nav>
        <RouterLink to="/modules">Modules</RouterLink>
        <RouterLink to="/projects">Projects</RouterLink>
      </nav>
      <button @click="logout">Logout</button>
    </header>
    <main>
      <RouterView />
    </main>
  </div>
</template>
