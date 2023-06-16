import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.siapabisa.R
import com.capstone.siapabisa.databinding.ItemNotificationBinding
import com.capstone.siapabisa.dummy.Notifs

class ListNotificationAdapter(
    private val data: MutableList<Notifs> = mutableListOf()
) :
    RecyclerView.Adapter<ListNotificationAdapter.UserViewHolder>() {

    fun setData(data: MutableList<Notifs>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemNotificationBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: Notifs) {
            v.apply{
                tvCompanyName.text = item.namaUsaha
                tvMessage.text = item.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}