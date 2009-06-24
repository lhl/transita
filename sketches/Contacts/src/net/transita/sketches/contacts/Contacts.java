package net.transita.sketches.contacts;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Contacts extends ListActivity {
	private ListAdapter mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Cursor C = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
		startManagingCursor(C);

		String[] columns = new String[] {People.NAME};
		int[] names = new int[] {R.id.row_entry};

		mAdapter = new SimpleCursorAdapter(this, R.layout.main, C, columns, names);
		setListAdapter(mAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(Intent.ACTION_CALL);
		Cursor C = (Cursor) mAdapter.getItem(position);
		long phoneId = C.getLong(C.getColumnIndex(People.PRIMARY_PHONE_ID));
		i.setData(Phones.CONTENT_URI.EMPTY);
		//i.setData(Phones.CONTENT_URI.addId(phoneId));
		startActivity(i);
	}
}