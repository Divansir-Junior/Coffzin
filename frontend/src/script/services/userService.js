import { API_BASE_URL, getErrorMessage, parseResponse } from "./api.js";

export async function createUser(data) {
    const response = await fetch(`${API_BASE_URL}/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(data)
    });

    const payload = await parseResponse(response);

    if (!response.ok) {
        throw new Error(getErrorMessage(payload, "Unable to create account"));
    }

    return payload;
}

export async function getCurrentUser() {
    const response = await fetch(`${API_BASE_URL}/users/me`, {
        credentials: "include"
    });

    if (response.status === 401) {
        return null;
    }

    const payload = await parseResponse(response);

    if (!response.ok) {
        throw new Error(getErrorMessage(payload, "Unable to fetch current user"));
    }

    return payload;
}
