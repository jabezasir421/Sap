import { apiClient } from "./client";
import type { CreateProjectRequest, Project } from "@/types/project";

export async function listProjects(): Promise<Project[]> {
  const { data } = await apiClient.get<Project[]>("/projects");
  return data;
}

export async function createProject(input: CreateProjectRequest): Promise<Project> {
  const { data } = await apiClient.post<Project>("/projects", input);
  return data;
}
