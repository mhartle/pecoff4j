/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Amir Szekely
 *******************************************************************************/
package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceEntry;
import org.boris.pecoff4j.constant.ResourceType;
import org.boris.pecoff4j.io.PEParser;
import org.boris.pecoff4j.io.ResourceParser;
import org.boris.pecoff4j.resources.StringFileInfo;
import org.boris.pecoff4j.resources.StringTable;
import org.boris.pecoff4j.resources.VersionInfo;
import org.boris.pecoff4j.util.ResourceHelper;

public class VersionStringsTest {

	public static void main(String[] args) throws IOException {
		testVersionStrings("C:/windows/system32/notepad.exe");
		testVersionStrings("C:/windows/system32/ieframe.dll");
		testVersionStrings("C:/windows/system32/igfxtray.exe");
	}

	public static void testVersionStrings(String path) throws IOException {
		PE pe = PEParser.parse(path);
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd,
				ResourceType.VERSION_INFO);
		for (int i = 0; i < entries.length; i++) {
			byte[] data = entries[i].getData();
			VersionInfo version = ResourceParser.readVersionInfo(data);

			StringFileInfo strings = version.getStringFileInfo();
			StringTable table = strings.getTable(0);
			for (int j = 0; j < table.getCount(); j++) {
				String key = table.getString(j).getKey();
				String value = table.getString(j).getValue();
				System.out.println(key + " = " + value);
			}
		}
	}

}