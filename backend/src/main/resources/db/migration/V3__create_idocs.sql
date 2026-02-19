CREATE TABLE idocs (
    id UUID PRIMARY KEY,
    tenant_id VARCHAR(80) NOT NULL,
    message_type VARCHAR(80) NOT NULL,
    direction VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_idocs_tenant_created_at ON idocs (tenant_id, created_at DESC);
