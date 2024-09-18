/**
 * Preference input data builder is a part of the project: https://github.com/pponec/schulze-method
 * Usage in the cell: `=PREFERENCES(B3;"C";C3:G3; 3)`
 * @param {number} count Number of voters per ballot.
 * @param {string} firstColName A character of the first elected candidate.
 * @param {array} array Array of preferences.
 * @param {number} defValue Default priority in case the value is missing.
 * @return Text format with a description of preferences, the group separator is a dash.
 * @customfunction
*/
function PREFERENCES(count, firstColName, array, defValue) {
    // Check input values:
    if (!array || array.length !== 1 || array[0].length < 2) {
        return 'ERROR';
    }
    if (count === null || count === '' || count === undefined) {
        return '';
    }
    if (!Number.isInteger(count)) {
        count = 1;
    }
    if (typeof firstColName !== 'string' || !firstColName.length) {
         firstColName = 'A';
    }
    const row = array[0];

    // Replace invalid cell values:
    const values = row.map(value => {
        return Number.isInteger(value) ? value : defValue;
    });

    // Sort unique values
    const uniqueSortedValues = [...new Set(values)].sort((a, b) => a - b);
    const groups = uniqueSortedValues.map(() => []);

    // Split index to columns by values
    values.forEach((value, index) => {
        groups[uniqueSortedValues.indexOf(value)].push(
            String.fromCharCode(firstColName.charCodeAt(0) + index));
    });

    // Build the result
    const result = count + ':' + groups.map(group => group.join('')).join('-');
    return result;
}