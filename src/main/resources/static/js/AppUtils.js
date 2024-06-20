/** WRITE AS EXPORT */

/**
 * Add the style-link for a component to the head
 */
export default function addCSS(path) {
    const styleSheet = document.createElement('link');
    styleSheet.rel = 'stylesheet';
    styleSheet.href = path;
    document.head.appendChild(styleSheet);
}

