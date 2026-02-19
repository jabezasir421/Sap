<script setup lang="ts">
import { useQuery } from "@tanstack/vue-query";
import { listModules } from "@/api/modules";

const modulesQuery = useQuery({
  queryKey: ["modules"],
  queryFn: listModules
});
</script>

<template>
  <section class="module-grid">
    <p v-if="modulesQuery.isLoading.value">Loading modules...</p>
    <p v-else-if="modulesQuery.isError.value">Could not load modules.</p>
    <article class="card module-card" v-for="item in modulesQuery.data.value ?? []" :key="item.module">
      <p class="module-chip">{{ item.status }}</p>
      <h2>{{ item.module }}</h2>
      <p class="module-scope">{{ item.reportingScope }}</p>
      <p>{{ item.description }}</p>
      <p v-if="item.criticalNote" class="critical-note">{{ item.criticalNote }}</p>
      <ul class="module-capabilities">
        <li v-for="capability in item.capabilities" :key="capability">{{ capability }}</li>
      </ul>
    </article>
  </section>
</template>
