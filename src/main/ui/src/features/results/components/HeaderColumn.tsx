import React from "react";

interface ColumnProps {
  display: string
}

export function HeaderColumn(props: ColumnProps) {

    function formatDate(display: string) {
        const date = new Date(display);
        return date.getDate() + '/' + date.getMonth() + 1 + '/' + date.getFullYear() + ' '
            + date.getHours() + ':' + date.getMinutes();
    }

    return (
        <td>
            {formatDate(props.display)}
        </td>
    );
}
