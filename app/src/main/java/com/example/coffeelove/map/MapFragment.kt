package com.example.coffeelove.map

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.coffeelove.R
import com.example.coffeelove.coffee.CoffeeViewModel
import com.example.coffeelove.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.Session.SearchListener
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError

class MapFragment : Fragment(),CameraListener {

    private var _binding : FragmentMapBinding?=null
    private val viewModel: CoffeeViewModel by activityViewModels<CoffeeViewModel>()
    private val mBinding get()=_binding!!

    private lateinit var mapView: MapView
    private lateinit var userLocationLayer:UserLocationLayer
    private lateinit var searchManager: SearchManager
    private lateinit var searchSession:Session
    private lateinit var searchEdit:EditText


    private fun submitQuery(query: String) {
        searchSession = searchManager.submit(
            query,
            VisibleRegionUtils.toPolygon(mapView.mapWindow.map.visibleRegion),
            SearchOptions(),
            searchListener
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentMapBinding.inflate(inflater,container, false)
        //Перенести вызов в сплеш
        viewModel.addBackGroundUppLoad()
        MapKitFactory.initialize(requireContext())
        mapView=mBinding.mapView

        mapView.mapWindow.map.isRotateGesturesEnabled = true
        mapView.mapWindow.map.move(
            CameraPosition(Point(55.7515, 37.64), 14.0F, 0.0F, 0.0F)
        )


        requestLocationPermission()

        val mapKit=MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()
        userLocationLayer=mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible=true
        userLocationLayer.isHeadingEnabled=true
        userLocationLayer.setObjectListener(locationObjectListener)



        searchManager=SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        searchEdit=mBinding.searchEdit
        mapView.mapWindow.map.addCameraListener(this)
        searchEdit.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitQuery(searchEdit.text.toString())
            }
            false
        }
        mapView.mapWindow.map.move(
            CameraPosition(Point(55.7515, 37.64), 14.0f, 0.0f, 0.0f)
        )

        submitQuery(searchEdit.text.toString())
        return mBinding.root
    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


    fun requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(),"android.permission.ACCESS_FINE_LOCATION")
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf("android.permission.ACCESS_FINE_LOCATION"),1)
        }
    }




    //устанавливаем дизайн для иконки местоположения пользователя
    private val locationObjectListener: UserLocationObjectListener =
        object : UserLocationObjectListener {
            override fun onObjectAdded(userLocationView: UserLocationView) {
                userLocationLayer.setAnchor(
                    PointF(((mapView.width*0.5).toFloat()),((mapView.height*0.5).toFloat())),
                    PointF(((mapView.width*0.5).toFloat()),((mapView.height*0.83).toFloat()))
                )
                userLocationView.accuracyCircle.fillColor = Color.argb(160,139,79,37)

                userLocationView.arrow.setIcon(
                    ImageProvider.fromResource(context, R.drawable.location_icon),
                    IconStyle().setAnchor(PointF(0.5f, 0.7f))
                        .setRotationType(RotationType.NO_ROTATION)
                        .setScale(0.065f))

                userLocationView.pin.setIcon(
                    ImageProvider.fromResource(context, R.drawable.location_icon),
                    IconStyle().setAnchor(PointF(0.5f, 0.7f))
                        .setRotationType(RotationType.NO_ROTATION)
                        .setScale(0.065f))

            }
            override fun onObjectRemoved(p0: UserLocationView) {}
            override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}
        }


    private val searchListener:SearchListener=
        object :SearchListener{
            override fun onSearchResponse(response: Response) {
                val mapObjects = mapView.mapWindow.map.mapObjects
                mapObjects.clear()
                for (searchResult in response.getCollection().getChildren()) {
                    val resultLocation = searchResult.obj!!.geometry[0].point
                    if (resultLocation != null) {
                        mapObjects.addPlacemark { placemark: PlacemarkMapObject ->
                            placemark.geometry = resultLocation
                            placemark.setIcon(ImageProvider.fromResource(context, R.drawable.search_result),
                                IconStyle().setAnchor(PointF(0.5f, 0.7f))
                                    .setRotationType(RotationType.NO_ROTATION)
                                    .setScale(0.4f))
                        }
                    }
                }
            }

            override fun onSearchError(error: Error) {
                var errorMessage = "Unknown error"
                if (error is RemoteError) {
                    errorMessage = "Remote server error"
                } else if (error is NetworkError) {
                    errorMessage = "Network error"
                }

                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }

        }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        Log.d("RRR","SUBMIT")
        if (finished) {
            submitQuery(searchEdit.text.toString())
        }
    }
}