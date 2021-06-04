export function getYearsList() {
    const _years: any[] = ["all"];
    for (let i = 2021; i >= 1980; i--) {
        _years.push(i);
    }

    return _years;
}