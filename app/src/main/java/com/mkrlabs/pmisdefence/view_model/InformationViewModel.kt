package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkrlabs.pmisdefence.model.InformationModel
import com.mkrlabs.pmisdefence.repository.InformationRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Abdullah on 7/30/2023.
 */
@HiltViewModel

class InformationViewModel @Inject constructor(val repository: InformationRepository)  :ViewModel() {

    var informationModelState : MutableLiveData<Resource<InformationModel>> = MutableLiveData()

     fun getProjectInformation(){
         informationModelState.postValue(Resource.Loading())

        viewModelScope.launch {
            repository.getDeveloperInformation {
                informationModelState.postValue(it)
            }
        }
    }

}