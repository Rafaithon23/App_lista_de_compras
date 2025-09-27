# Shopping List App - TODO List

## RF001: Login/Logout
- [x] Implement login with email/password validation
- [x] Add mock user for testing
- [x] Handle login success/failure with Toast messages
- [x] Add logout functionality in MainActivity (toolbar menu or button)
- [x] Ensure logout clears currentUser and returns to LoginActivity

## RF002: User Registration
- [x] Implement registration form with validation
- [x] Validate email format and password confirmation
- [x] Save new users to DataManager
- [x] Prevent duplicate email registration
- [ ] Test registration flow end-to-end

## RF003: Manage Shopping Lists
- [x] Update MainActivity to use ViewBinding
- [x] Add RecyclerView to activity_suas_listas.xml for list display
- [x] Create ListAdapter.kt for RecyclerView
- [x] Implement FAB in MainActivity for adding new lists
- [x] Handle list item clicks to open ListDetailActivity
- [x] Create AddEditListActivity.kt
- [x] Create activity_add_edit_list.xml layout
- [x] Implement create/edit/delete list functionality
- [ ] Add image picker for list images (optional)
- [x] Update list display with images and titles
- [ ] Add swipe-to-delete or long-press delete for lists

## RF004: Manage List Items
- [x] Create ListDetailActivity.kt
- [x] Create activity_list_detail.xml layout with RecyclerView
- [x] Create ItemAdapter.kt for item display
- [x] Implement FAB in ListDetailActivity for adding items
- [x] Create AddEditItemActivity.kt
- [x] Create activity_add_edit_item.xml layout
- [x] Implement create/edit/delete item functionality
- [x] Add fields: name, quantity, unit, category, bought checkbox
- [x] Handle item bought/unbought toggle
- [ ] Add swipe-to-delete for items
- [x] Display items sorted by name

## RF005: Search
- [ ] Add SearchView to MainActivity toolbar
- [ ] Implement search filtering for lists by title
- [ ] Add search in ListDetailActivity for items by name
- [ ] Handle search query changes and update RecyclerViews

## Navigation and UI Polish
- [x] Update activity_suas_listas.xml with Material Design components
- [x] Add toolbar to MainActivity with logout option
- [ ] Ensure proper back navigation between activities
- [ ] Handle data refresh when returning from edit activities
- [ ] Remove or integrate SuasListasActivity.kt
- [ ] Update AndroidManifest.xml if needed for new activities
- [ ] Add proper themes and styles for Material Design
- [ ] Handle configuration changes (orientation, etc.)

## Testing and Final Touches
- [ ] Test all CRUD operations for lists and items
- [ ] Test login/logout flow
- [ ] Test registration and login with new users
- [ ] Test search functionality
- [ ] Add proper error handling and user feedback
- [ ] Ensure app handles empty states (no lists, no items)
- [ ] Test with different screen sizes and orientations
- [ ] Final UI/UX review and polish
