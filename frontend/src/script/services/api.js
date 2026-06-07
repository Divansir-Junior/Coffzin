export const API_BASE_URL = "http://localhost:8080/api";

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
