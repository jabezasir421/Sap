import { apiClient } from "./client";
import type { ErpModule } from "@/types/module";

export async function listModules(): Promise<ErpModule[]> {
  const { data } = await apiClient.get<ErpModule[]>("/modules");
  return data;
}
