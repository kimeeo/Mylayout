function array_flip(trans) {
	var key, tmp_ar = {};
	for (key in trans) {
	    tmp_ar[trans[key]] = key;
	}
    return tmp_ar;
};

function get_objects(tables,table_name,where_index,match_value) {
		var objects = [];
		for (var record in tables[table_name]['records']) {
			record = tables[table_name]['records'][record];
			if (!where_index || record[where_index]==match_value) {
				var object = {};
				for (var index in tables[table_name]['columns']) {
					var column = tables[table_name]['columns'][index];
					object[column] = record[index];
					for (var relation in tables) {
						var reltable = tables[relation];
						for (var key in reltable['relations']) {
							var target = reltable['relations'][key];
							if (target == table_name+'.'+column) {
								column_indices = array_flip(reltable['columns']);
								object[relation] = get_objects(tables,relation,column_indices[key],record[index]);
							}
						}
					}
				}
				objects.push(object);
			}
		}
		return objects;
};

function php_crud_api_transform(tables)
{
    tables =JSON.parse(tables);
	tree = {};
	for (var name in tables) {
		var table = tables[name];
		if (!table['relations'])
		{
			tree[name] = get_objects(tables,name);
			if (table['results']) {
				tree['_results'] = table['results'];
			}
		}
	}
	return JSON.stringify(tree);
}