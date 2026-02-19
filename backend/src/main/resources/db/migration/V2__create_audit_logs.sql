CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    tenant_id VARCHAR(80) NOT NULL,
    username VARCHAR(160) NOT NULL,
    method VARCHAR(10) NOT NULL,
    path VARCHAR(300) NOT NULL,
    status_code INTEGER NOT NULL,
    duration_ms BIGINT NOT NULL,
    client_ip VARCHAR(64) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_audit_logs_tenant_created_at ON audit_logs (tenant_id, created_at DESC);
