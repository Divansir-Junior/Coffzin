const API_PORT = "8080";

function resolveApiBaseUrl() {
    const fallbackHost = "localhost";

    if (!window.location || window.location.protocol === "file:") {
        return `http://${fallbackHost}:${API_PORT}/api`;
    }

    const host = window.location.hostname || fallbackHost;
    return `${window.location.protocol}//${host}:${API_PORT}/api`;
}

export const API_BASE_URL = resolveApiBaseUrl();

export async function parseResponse(response) {
    const contentType = response.headers.get("content-type") || "";

    if (!contentType.includes("application/json")) {
        return { message: await response.text() };
    }

    return response.json();
}

export function getErrorMessage(payload, fallback = "Request failed") {
    if (!payload) return fallback;
    if (payload.errors) return Object.values(payload.errors).join("\n");
    if (payload.message) return payload.message;

    return fallback;
}
