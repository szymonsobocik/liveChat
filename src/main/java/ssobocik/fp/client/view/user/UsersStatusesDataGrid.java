package ssobocik.fp.client.view.user;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.view.user.manage.UserSelectedHandler;
import ssobocik.fp.dto.UserDTO;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * List on the left side with users and their statuses
 *
 * @author szymon.sobocik
 */
public class UsersStatusesDataGrid extends DataGrid<UserDTO> {

    static final Logger logger = Logger.getLogger(UsersStatusesDataGrid.class.getName());

    ListDataProvider<UserDTO> dataProvider = new ListDataProvider<UserDTO>();

    private UserSelectedHandler userSelectedHandler;

    private static final int DEFAULT_PAGESIZE = 100;

    public UsersStatusesDataGrid() {
        super(DEFAULT_PAGESIZE, GWT.<DataGrid.SelectableResources>create(DataGrid.SelectableResources.class));
        initTable();
    }

    public void setUserSelectedHandler(UserSelectedHandler userSelectedHandler) {
        this.userSelectedHandler = userSelectedHandler;
    }

    public void setUsers(List<UserDTO> users) {
        dataProvider.getList().clear();
        dataProvider.getList().addAll(users);
    }

    private void initTable() {
        setEmptyTableWidget(new Label("Please add data."));

        initColumnId();
        initColumnUsername();
        initColumnNickname();

        final SingleSelectionModel<UserDTO> selectionModel = new SingleSelectionModel<UserDTO>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                UserDTO user = selectionModel.getSelectedObject();
                logger.info("selected user: " + user.getUsername());
                if (userSelectedHandler != null) {
                    userSelectedHandler.selectedUser(user);
                }
            }
        });

        setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
        setSelectionModel(selectionModel);
        dataProvider.addDataDisplay(this);
    }


    private void initColumnId() {
        TextColumn<UserDTO> idCol = new TextColumn<UserDTO>() {

            @Override
            public String getValue(UserDTO object) {
                return String.valueOf(object.getId());
            }
        };
        idCol.setSortable(false);
        addColumn(idCol, "#");
        getColumnSortList().push(idCol);
    }

    private void initColumnUsername() {
        TextColumn<UserDTO> userNameCol = new TextColumn<UserDTO>() {

            @Override
            public String getValue(UserDTO object) {
                return object.getUsername();
            }
        };
        userNameCol.setSortable(true);
        addColumn(userNameCol, FClientUtils.messages.lbUsername());

        ColumnSortEvent.ListHandler<UserDTO> userNameColHandler = new ColumnSortEvent.ListHandler<UserDTO>(dataProvider.getList());
        userNameColHandler.setComparator(userNameCol, new Comparator<UserDTO>() {

            @Override
            public int compare(UserDTO o1, UserDTO o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
        addColumnSortHandler(userNameColHandler);
    }

    private void initColumnNickname() {
        TextColumn<UserDTO> column = new TextColumn<UserDTO>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getNickname();
            }
        };
        column.setSortable(true);
        addColumn(column, FClientUtils.messages.lbNickname());

        ColumnSortEvent.ListHandler<UserDTO> colHandler = new ColumnSortEvent.ListHandler<UserDTO>(dataProvider.getList());
        colHandler.setComparator(column, new Comparator<UserDTO>() {
            @Override
            public int compare(UserDTO o1, UserDTO o2) {
                if (o1.getNickname() == null){
                    return 1;
                }
                return o1.getNickname().compareTo(o2.getNickname());
            }
        });
        addColumnSortHandler(colHandler);
    }

}
