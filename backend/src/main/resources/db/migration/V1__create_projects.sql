CREATE TABLE projects (
    id UUID PRIMARY KEY,
    tenant_id VARCHAR(80) NOT NULL,
    name VARCHAR(140) NOT NULL,
    description VARCHAR(1500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_projects_tenant_created_at ON projects (tenant_id, created_at DESC);
