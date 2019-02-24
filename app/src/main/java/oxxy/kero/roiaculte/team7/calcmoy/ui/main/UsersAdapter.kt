//package oxxy.kero.roiaculte.team7.calcmoy.ui.main
//
//import android.content.Context
//import android.support.constraint.ConstraintLayout
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import de.hdodenhof.circleimageview.CircleImageView
//import oxxy.kero.roiaculte.team7.domain.models.User
//
//class UsersAdapter(val context :Context , val list: List<User>):RecyclerView.Adapter<UsersAdapter.UserHolder> (){
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserHolder {
//
//    }
//
//    override fun getItemCount() = list.size
//
//    override fun onBindViewHolder(p0: UserHolder, p1: Int) {
//        p0.updateUser(list[p1])
//    }
//
//    class UserHolder(v:View): RecyclerView.ViewHolder(v){
//        private lateinit var constraintLayout:ConstraintLayout
//        private lateinit var  image :CircleImageView
//        private lateinit var textView :TextView
//        fun updateUser(user: User) {
//
//        }
//
//    }
//}