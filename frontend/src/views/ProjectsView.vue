<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";
import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query";
import { createProject, listProjects } from "@/api/projects";

const queryClient = useQueryClient();
const name = ref("");
const description = ref("");
let projectEvents: EventSource | null = null;

const projectsQuery = useQuery({
  queryKey: ["projects"],
  queryFn: listProjects
});

const createMutation = useMutation({
  mutationFn: createProject,
  onSuccess: () => {
    name.value = "";
    description.value = "";
    queryClient.invalidateQueries({ queryKey: ["projects"] });
  }
});

function submit() {
  if (!name.value) {
    return;
  }

  createMutation.mutate({
    name: name.value,
    description: description.value
  });
}

onMounted(() => {
  const token = localStorage.getItem("access_token");
  if (!token) {
    return;
  }

  projectEvents = new EventSource(`/api/v1/stream/projects?access_token=${encodeURIComponent(token)}`);
  projectEvents.addEventListener("project-created", () => {
    queryClient.invalidateQueries({ queryKey: ["projects"] });
  });
});

onBeforeUnmount(() => {
  projectEvents?.close();
  projectEvents = null;
});
</script>

<template>
  <section class="grid">
    <article class="card">
      <h2>Create project</h2>
      <label>
        Name
        <input v-model="name" type="text" placeholder="Billing rollout" />
      </label>
      <label>
        Description
        <textarea v-model="description" rows="3" placeholder="Optional notes" />
      </label>
      <button :disabled="createMutation.isPending.value" @click="submit">Create</button>
    </article>

    <article class="card">
      <h2>Projects</h2>
      <p v-if="projectsQuery.isLoading.value">Loading...</p>
      <p v-else-if="projectsQuery.isError.value">Could not load projects.</p>
      <ul v-else>
        <li v-for="project in projectsQuery.data.value" :key="project.id">
          <strong>{{ project.name }}</strong>
          <span>{{ new Date(project.createdAt).toLocaleString() }}</span>
          <p>{{ project.description }}</p>
        </li>
      </ul>
    </article>
  </section>
</template>
