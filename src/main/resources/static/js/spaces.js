export function initSpaces() {
    const container = document.querySelector('.spaces-scroll');
    if (!container) return;

    container.addEventListener('click', (e) => {
        const header = e.target.closest('.space-header');
        if (!header) return;

        // Не реагируем на клики по контекстному меню
        if (e.target.closest('.context-trigger')) return;

        const item = header.closest('.space-item');
        if (item) {
            item.classList.toggle('expanded');
        }
    });
}