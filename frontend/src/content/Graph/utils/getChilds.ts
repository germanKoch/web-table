export function getChilds(graph) {
    return {
        name: graph.name,
        children: graph.fields.map((g) => {
            if (g?.fields) {
                return {
                    name: g?.name,
                    children: Array.isArray(getChilds(g)) ? getChilds(g) : [getChilds(g)],
                };
            } else {
                return {
                    name: g?.name,
                };
            }
        }),
    };
}