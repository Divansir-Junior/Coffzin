export function formatDateToISO(dateString) {
    const date = new Date(dateString);

    if (isNaN(date.getTime())) {
        throw new Error("Data de nascimento inválida");
    }

    return date.toISOString().split("T")[0];
}
